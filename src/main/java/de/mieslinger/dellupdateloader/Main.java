/*
 * The MIT License
 *
 * Copyright 2021 mieslingert.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package de.mieslinger.dellupdateloader;

import com.sampullara.cli.Argument;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sampullara.cli.Args;
import de.mieslinger.dellupdateloader.Models.DriverListData;
import de.mieslinger.dellupdateloader.Models.FileFrmtInfo;
import de.mieslinger.dellupdateloader.Models.OthFileFrmt;
import de.mieslinger.dellupdateloader.Models.Root;
import java.io.File;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author mieslingert
 */
public class Main {

    @Argument(alias = "m", description = "model, i.e. r320")
    private static String model = "r6415";

    @Argument(alias = "o", description = "os, i.e. RHE70 RHEL8 WST14 WS19L")
    private static String os = "WS19L";

    @Argument(alias = "c", description = "common dir where all DUP are downloaded to, i.e. /srv/http/ftpdellcom-mirror.svc.1u1.it")
    private static String commondir = "/srv/http/ftpdellcom-mirror.svc.1u1.it";

    @Argument(alias = "s", description = "outputdir /srv/http/dell-repo-catalog.svc.1u1.it/$model")
    private static String perserverdir = "/srv/http/dell-repo-catalog.svc.1u1.it";

    @Argument(alias = "d", description = "dlscript=/tmp/get-${model}.sh")
    private static String dlscript = "bla";

    @Argument(alias = "l", description = "perserverLinkScript=/tmp/link-${model}.sh")
    private static String perserverlinks = "blubb";

    @Argument(alias = "p", description = "Proxy name or IP")
    private static String proxyName = "blubb";

    @Argument(alias = "P", description = "ProxyPort")
    private static String proxyPort = "3128";

    private static Logger logger = LoggerFactory.getLogger(Main.class);
    private static String updType = null;
    private static PrintWriter outFile = null;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        switch (os) {
            case "RHE70":
                updType = "LLXP";
                break;
            case "RHEL8":
                updType = "LLXP";
                break;
            case "WST14":
                updType = "LW64";
                break;
            case "WS19L":
                updType = "LW64";
                break;

            default:
                System.out.println("os: " + os + " is unsupported. Fix Source");
                System.exit(1);
                break;
        }

        try {
            List<String> unparsed = Args.parseOrExit(Main.class, args);

            String urlStr = "https://www.dell.com/support/driver/de-de/ips/api/driverlist/fetchdriversbyproduct?productcode=poweredge-" + model + "&oscode=" + os + "&lob=PowerEdge";
            logger.debug("url: " + urlStr);

            URL url = new URL(urlStr);
            HttpURLConnection con;
            if (proxyName.equals("blubb")) {
                con = (HttpURLConnection) url.openConnection();
            } else {
                Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyName, Integer.parseInt(proxyPort)));
                con = (HttpURLConnection) url.openConnection(proxy);
            }
            con.setRequestMethod("GET");
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("accept", "application/json");
            con.setRequestProperty("X-Requested-With", "XMLHttpRequest");
            con.setConnectTimeout(5000);
            con.setReadTimeout(5000);

            long step = System.currentTimeMillis() - start;
            logger.debug("Connection setup: {}ms", step);

            int status = con.getResponseCode();
            step = System.currentTimeMillis() - start;
            logger.debug("con.getResponseCode: {}, {}ms since start", status, step);

            ObjectMapper om = new ObjectMapper();
            Root root = om.readValue(con.getInputStream(), Root.class);
            con.disconnect();

            step = System.currentTimeMillis() - start;
            logger.debug("download and parsing done: {}ms since start", step);

            outFile = new PrintWriter(new File(dlscript));

            outFile.printf("cd %s\n", commondir);
            for (DriverListData d : root.driverListData) {
                FileFrmtInfo ffi = d.fileFrmtInfo;
                URL u = new URL(ffi.httpFileLocation);
                String path = u.getFile().substring(1);
                if (ffi.fileType.equals(updType)) {
                    outFile.printf("if [ ! -f '%s' ]; then mkdir -p $(dirname '%s'); wget -q -O '%s' '%s'; fi\n", path, path, path, ffi.httpFileLocation);
                    outFile.printf("if [ ! -f '%s.html' ]; then  wget -q -O '%s.html' '%s%s'; fi\n", path, path, "https://www.dell.com/support/home/de/de/debsdt1/drivers/driversdetails?driverId=", d.driverId);
                }
            }
            outFile.close();

            outFile = new PrintWriter(new File(perserverlinks));
            outFile.printf("rm -rf %s/%s/%s\n", perserverdir, "poweredge-" + model, os);
            outFile.printf("mkdir -p %s/%s/%s\n", perserverdir, "poweredge-" + model, os);
            outFile.printf("cd %s/%s/%s\n", perserverdir, "poweredge-" + model, os);
            for (DriverListData d : root.driverListData) {
                FileFrmtInfo ffi = d.fileFrmtInfo;
                if (ffi.fileType.equals(updType)) {
                    URL u = new URL(ffi.httpFileLocation);
                    String path = u.getFile().substring(1);
                    outFile.printf("ln -s '%s/%s' $(basename '%s')\n", commondir, path, path);
                    outFile.printf("ln -s '%s/%s.html' $(basename '%s').html\n", commondir, path, path);
                }
                for (OthFileFrmt ofmt : d.othFileFrmts) {
                    if (ofmt.fileType.equals(updType)) {
                        URL u = new URL(ofmt.httpFileLocation);
                        String path = u.getFile().substring(1);
                        outFile.printf("ln -s '%s/%s' $(basename '%s')\n", commondir, path, path);
                        outFile.printf("ln -s '%s/%s.html' $(basename '%s').html\n", commondir, path, path);
                    }
                }
            }
            outFile.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
