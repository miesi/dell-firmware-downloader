#!/bin/bash
#
# All config around goes here
#

# If you need a proxy, put it here for wget in the scripzts
export http_proxy=http://myproxy:3128/
export https_proxy=http://myproxy:3128/

for model in r320 r620 r720 r720xd r820 r920 r230 r330 r430 r630 r730 r730xd r930 r240 r340 r440 r640 r740 r740xd r6415; do
	mkdir -p /srv/http/my-per-server-directory/poweredge-$model
	/usr/local/sbin/ddl [ -p myproxy -P 3128 ] -m $model -c /srv/http/my-dell-com-dir -s /srv/http/my-dir-with-per-server-dirs -d /tmp/get-${model}.sh -l /tmp/link-${model}.sh
	chmod 755 /tmp/get-${model}.sh
	chmod 755 /tmp/link-${model}.sh
        /tmp/get-${model}.sh
	/tmp/link-${model}.sh
	rm /tmp/get-${model}.sh
	rm /tmp/link-${model}.sh
done

