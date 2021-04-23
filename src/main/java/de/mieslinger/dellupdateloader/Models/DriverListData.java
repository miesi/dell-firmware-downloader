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
package de.mieslinger.dellupdateloader.Models;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;
import java.util.List;

/**
 *
 * @author mieslingert
 */
public class DriverListData {

    @JsonProperty("DriverId")
    public String driverId;
    @JsonProperty("DriverName")
    public String driverName;
    @JsonProperty("Type")
    public String type;
    @JsonProperty("TypeName")
    public String typeName;
    @JsonProperty("Imp")
    public String imp;
    @JsonProperty("ImpId")
    public String impId;
    @JsonProperty("ReleaseDate")
    public String releaseDate;
    @JsonProperty("IsSecure")
    public boolean isSecure;
    @JsonProperty("IsRestricted")
    public boolean isRestricted;
    @JsonProperty("IsRestart")
    public boolean isRestart;
    @JsonProperty("DellVer")
    public String dellVer;
    @JsonProperty("BrfDesc")
    public String brfDesc;
    @JsonProperty("AppOses")
    public List<String> appOses;
    @JsonProperty("AppLanguages")
    public List<String> appLanguages;
    @JsonProperty("FileFormats")
    public List<String> fileFormats;
    @JsonProperty("FileFrmtInfo")
    public FileFrmtInfo fileFrmtInfo;
    @JsonProperty("OthFileFrmts")
    public List<OthFileFrmt> othFileFrmts;
    @JsonProperty("Cat")
    public String cat;
    @JsonProperty("CatName")
    public String catName;
    @JsonProperty("IsBiosFirmWarningToBeShown")
    public boolean isBiosFirmWarningToBeShown;
    @JsonProperty("IsOthVerExst")
    public boolean isOthVerExst;
    @JsonProperty("LUPDDate")
    public String lUPDDate;
    @JsonProperty("IsCertDriver")
    public boolean isCertDriver;
    @JsonProperty("ReleaseDateValue")
    public Date releaseDateValue;
    @JsonProperty("DeviceNameWithLan")
    public Object deviceNameWithLan;
    @JsonProperty("OtherVersions")
    public List<String> otherVersions;
    @JsonProperty("Predecessor")
    public String predecessor;
    @JsonProperty("Successor")
    public String successor;
    @JsonProperty("OtherVersionImportance")
    public boolean otherVersionImportance;
    @JsonProperty("IsImpInfoAvailable")
    public int isImpInfoAvailable;
    @JsonProperty("IsTagDriver")
    public int isTagDriver;
    @JsonProperty("Rank")
    public int rank;
    @JsonProperty("DownloadType")
    public String downloadType;
    @JsonProperty("AccessLevel")
    public String accessLevel;
}
