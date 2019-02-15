#!/usr/bin/env python36

import sys
import json
import re
import requests
from tabulate import tabulate
from collections import OrderedDict
import argparse

def fetch_json(product, oscode):
    url = 'http://www.dell.com/support/home/us/en/04/drivers/driverslist/platfromdriver'
    params = {
                'productCode': product, 
                'osCode': oscode, 
                'isTagResult': 'false', 
              }
    headers = {'X-Requested-With': 'XMLHttpRequest'}
    r = requests.get(url, headers=headers, params=params)

    if r.status_code != 200:
        print("Polling '{name}' failed with status {code} - Msg: {msg}".format(name=product, code=r.status_code, msg=r.text))
    js = r.json()
    #print("  got reply: {}".format(r))
    payload = js['DriverListData']
    js_payload = json.loads(payload.encode("utf8").decode())

    if args.debug:
        print (json.dumps(js_payload, indent=4, sort_keys=True))
    return js_payload


def parse_json(js, product=None):
    collect = list()

    for elem in js:
        if elem['fileFrmtInfo']['fileType'] == args.type and elem['catName'] != 'Systems Management' and elem['catName'] != 'Drivers for OS Deployment' and elem['catName'] != 'Lifecycle Controller – Legacy':
            # TODO: exclude "Systems-Management_Application_"
            # if elem['catName'] == 'BIOS':
            info = dict()
            info['catName'] = elem['catName']
            if product is not None:
                info['product'] = product
            match = re.fullmatch(r'([\d.]+) ,\1', elem['dellVer'])
            if match is not None:
                info['version'] = match.group(1)
            else:
                info['version'] = elem['dellVer']
            info['date_release'] = elem['releaseDate']
            if elem['lupdDate'] != elem['releaseDate']:
                info['date_update'] = elem['lupdDate']
            info['id'] = elem['driverId']
            info['url'] = elem['fileFrmtInfo']['httpFileLocation']
            info['name'] = elem['driverName']
            if args.debug:
                print("  Found ver '{ver}' for {prod}".format(ver=info['version'], prod=info['product']))
            collect.append(info)
    return collect


if __name__ == "__main__":
    parser = argparse.ArgumentParser(description='Check and download Firmware files for a specific Dell Hardware')
    parser.add_argument('-d', '--debug', action='store_const', const=42, help='Debug Messages')
    parser.add_argument('-l', '--download', action='store_const', const=42, help='download files')
    parser.add_argument('-m', '--model', help='Model to download firmware for e.g. poweredge-r740xd', default=None, required=True)
    parser.add_argument('-t', '--type', help='update type e.g. LLXP for Linux .BIN', default='LLXP')
    parser.add_argument('-o', '--oscode', help='osCode (e.g. RHE70) for RHEL 70', default='RHE70')
    parser.add_argument('-c', '--commondir', help='download files common directory (e.g. /srv/http/my-ftpdellcom-mirror)', default='/srv/http/my-ftpdellcom-mirror')
    parser.add_argument('-s', '--perserverdir', help='create softlinks from per server dir to commondir (e.g. /srv/http/my-ftpdellcom-mirror/poweredge-r240)', default=None)
    parser.add_argument('--report', help='write report to specified file', default=None)
    parser.add_argument('--dlscript', help='write download script to file', default=None)
    parser.add_argument('--perserverlinks', help='write script to create links in this file', default=None)

    args = parser.parse_args()

    data = list()
    data.append(OrderedDict([('product', 'product'), ('version', 'version'), ('date_release', 'reldate'), ('date_update', 'update'), ('id', 'id'), ('url', 'url'), ('name', 'name')]))
    elem = args.model
    if args.debug:
        print("Fetching '{}'".format(args.model))
    js = fetch_json(args.model, args.oscode)
    data = data + parse_json(js, args.model)

    if args.report:
        with open(args.report, 'w') as report_file:
            report_file.write(tabulate(data, headers="firstrow"))
        report_file.close();

    del data[0]
    if args.dlscript:
        with open(args.dlscript,'w') as dlscript_file:
            dlscript_file.write("cd " + args.commondir + "\n") 
            for elem in data:
                path = elem['url'][27:]
                url = elem['url']
                dlscript_file.write ("if [ ! -f '%s' ]; then mkdir -p $(dirname '%s'); wget -O '%s' '%s'; fi\n" % (path, path, path, url))
            dlscript_file.close()

    if args.perserverdir:
        with open(args.perserverlinks,'w') as perserverlinks_file:
            perserverlinks_file.write ("mkdir -p "+ args.perserverdir + "\n") # create if it does not exist
            perserverlinks_file.write ("cd " + args.perserverdir + "\n")
            perserverlinks_file.write ("mv report.txt ..\n") # save report.txt
            perserverlinks_file.write ("rm *\n") # remove old entries
            perserverlinks_file.write ("mv ../report.txt .\n") # restore report.txt
            for elem in data:
                path = elem['url'][27:]
                perserverlinks_file.write ("ln -s '%s/%s' $(basename '%s')\n" % (args.commondir, path, path))
            perserverlinks_file.close()

