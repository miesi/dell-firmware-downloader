#!/bin/bash
#
# All config around bios.py goes here
#

# If you need a proxy, put it here
export http_proxy=http://myproxy:3128/
export https_proxy=http://myproxy:3128/

for modelshort in r320 r620 r720 r720xd r820 r920 r230 r330 r430 r630 r730 r730xd r930 r240 r340 r440 r640 r740 r740xd r6415; do
	model="poweredge-$modelshort"
	mkdir -p /srv/http/my-per-server-directory/$model
	./bios.py -m $model -s /srv/http/my-per-server-directory/$model --report=/srv/http/my-per-server-directory/$model/report.txt --dlscript=/tmp/get-${model}.sh --perserverlinks=/tmp/link-${model}.sh
	chmod 755 /tmp/get-${model}.sh
	chmod 755 /tmp/link-${model}.sh
        /tmp/get-${model}.sh
	/tmp/link-${model}.sh
	rm /tmp/get-${model}.sh
	rm /tmp/link-${model}.sh
done

