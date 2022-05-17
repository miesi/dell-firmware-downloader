# dell-firmware-downloader
Download all Firmware files for a set of Server Types and OSes.

Dell also provides tooling for firmware updates on Linux, see comments below.

This script style Java tool download all available Files for a set of Server Types.

The files are saved in the same structure as on downloads.dell.com

In a second location a per server type directory is created with softlinks to all files for this server type.

To update a server you simply copy the content of this per server type directory to the server to update and run the file one by one.

## Build
You need to have a working environment for `mvn` command.

```
git clone https://github.com/miesi/dell-firmware-downloader
cd dell-firmware-downloader
mvn clean install
```

 should do the trick and produce a rpm in `target/`.

## Install
`yum install target/DellUpdateLoader-1.3-1.noarch.rpm`

This will automatically install java-headless.

The scripts are only tested with CentOS 7.

## Run

- adjust paths and server types in `src/main/bash/downloader.sh`
- run `downloader.sh` and get a coffee


## Thanks
I'd like to thank @frinke for his work on original `bios.py`. I removed it because
it references an outdated api at Dell.

## Dell tools

dsu - Dell System Update
- never got it to actually update any component
- did not manage to clone the repository to a local Server

iDRAC Lifecycle Controller
- based on a roughly 2 weeks old catalog
- once a update job for a device is created, it is not overwritten by a newer one
- sometimes it happens that a device to update gets stuck can not be updated any further (e.g. BIOS update to 1.5.6 did not work, so it is impossible to update to 1.6.x using any iDRAC method)
- some updates (e.g. 17.x intel X520) make the device unstable when run from Lifecycle Controller, but work just fine when run from any OS.
- Sometimes update jobs fail because iDRAC does not have enough Filesystem space to buffer the download
- Error messages regaring failed downloads are compeletely without information. Example:
"Unable to transfer a file, FOLDER05328926M/1/BIOS_CVHH4_WN64_1.6.12.EXE, because the file is not available at the remote host location."

To be usuable, the error message must contain:
The full url that was tried.
If a proxy was used
The exact error message: A 404 is something different from a 500, from DNS Name not found, from a timed out, from a connection refused.

# Things known to be problematic
- Some updates (e.g. intel Yorktown SSD FW) require a device powercycle.
- Some updates require some other minimum FW version to work (e.g. iDRAC 7 1.x must first be updated to 1.66.65 and can only then safely be updated further to 2.x FW)
