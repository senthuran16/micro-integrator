/*
 * Copyright (c) 2005-2010, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.wso2.carbon.esb.mediator.test.switchMediator;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.wso2.esb.integration.common.utils.CarbonLogReader;
import org.wso2.esb.integration.common.utils.ESBIntegrationTest;
import org.wso2.esb.integration.common.utils.ESBTestConstant;

public class FurtherProcessingOfSwitchAfterMatchTestCase extends ESBIntegrationTest {
    private CarbonLogReader carbonLogReader;

    @BeforeClass(alwaysRun = true)
    public void beforeClass() throws Exception {
        super.init();
        carbonLogReader = new CarbonLogReader();
    }

    @Test(groups = {
            "wso2.esb" }, description = "Switch Mediator: Test whether further processing of the switch block is done after a match is found.")
    public void testFurtherProcessingOfSwitch() throws Exception {
        carbonLogReader.start();

        axis2Client.sendSimpleStockQuoteRequest(
                getProxyServiceURLHttp("switchMediatorFurtherProcessingAfterMatchTestProxy"),
                getBackEndServiceUrl(ESBTestConstant.SIMPLE_STOCK_QUOTE_SERVICE), "IBM");

        //AsserTrue symbol property of INFO log for "Great stock - IBM"
        //!AssertTrue Test property of INFO log for "Oh no! IBM again?"
        Assert.assertTrue(carbonLogReader.assertIfLogExists("Great stock - IBM"), "Symbol property not set");
        Assert.assertFalse(carbonLogReader.assertIfLogExists("Oh no! IBM again?"), "Test property set unexpectedly");

        carbonLogReader.stop();
    }
}