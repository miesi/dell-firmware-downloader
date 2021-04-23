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
import java.util.List;

/**
 *
 * @author mieslingert
 */
public class Root {
    @JsonProperty("IsTag") 
    public boolean isTag;
    @JsonProperty("ServiceTag") 
    public Object serviceTag;
    @JsonProperty("ProductCode") 
    public String productCode;
    @JsonProperty("ProductName") 
    public Object productName;
    @JsonProperty("Lob") 
    public String lob;
    @JsonProperty("DriversFilter") 
    public Object driversFilter;
    @JsonProperty("DefaultCMSData") 
    public Object defaultCMSData;
    @JsonProperty("CMSData") 
    public Object cMSData;
    @JsonProperty("DriverListData") 
    public List<DriverListData> driverListData;
    @JsonProperty("Lwp") 
    public Object lwp;
    @JsonProperty("IsNoDriversFound") 
    public boolean isNoDriversFound;
    @JsonProperty("IsNoDriversFoundForTag") 
    public boolean isNoDriversFoundForTag;
    @JsonProperty("IsPredecessorData") 
    public boolean isPredecessorData;
    @JsonProperty("NoDriverMessage") 
    public Object noDriverMessage;
    @JsonProperty("DriverListMessage") 
    public Object driverListMessage;
    @JsonProperty("IsScanIcSourceDnd") 
    public boolean isScanIcSourceDnd;
    @JsonProperty("WinSupportedOs") 
    public boolean winSupportedOs;
    @JsonProperty("DocFileTypes") 
    public Object docFileTypes;
    @JsonProperty("IsRankAvailable") 
    public boolean isRankAvailable;
    @JsonProperty("IsSecureDriverAvailable") 
    public boolean isSecureDriverAvailable;
    @JsonProperty("HasSecureDownloadsOnly") 
    public boolean hasSecureDownloadsOnly;
    @JsonProperty("PopularPillsDisplayCount") 
    public Object popularPillsDisplayCount;
    public boolean isUbuntuMsgAvailable;
    @JsonProperty("UbuntuBICount") 
    public int ubuntuBICount;
    @JsonProperty("UbuntuWebPartMsg") 
    public Object ubuntuWebPartMsg;
    @JsonProperty("NlpSearchEnabled") 
    public boolean nlpSearchEnabled;
    @JsonProperty("FilterStopwords") 
    public boolean filterStopwords;
    @JsonProperty("TopnSimilarWords") 
    public Object topnSimilarWords;
    @JsonProperty("SearchApiEndpoint") 
    public Object searchApiEndpoint;
    @JsonProperty("ISGSignInMsg") 
    public boolean iSGSignInMsg;
    @JsonProperty("ISGWaringMsg") 
    public boolean iSGWaringMsg;
    @JsonProperty("IsAuthenticatedUser") 
    public boolean isAuthenticatedUser;
    @JsonProperty("UserType") 
    public Object userType;

}
