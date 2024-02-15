package com.linkly.pos.sdk.models.display;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import com.linkly.pos.sdk.common.MoshiUtil;
import com.linkly.pos.sdk.models.enums.GraphicCode;
import com.linkly.pos.sdk.models.enums.InputType;

class DisplayResponseTest {

    @Test
    void should_deserialize_success() throws IOException {
        String json = "{\"acceptYesKeyFlag\":true,\"authoriseKeyFlag\":true,\"cancelKeyFlag\":"
            + "true,\"declineNoKeyFlag\":true,\"displayText\":[\"display1\",\"display2\"],"
            + "\"graphicCode\":\"3\",\"inputType\":\"3\",\"lineLength\":20,\"numberOfLines\":"
            + "100,\"okKeyFlag\":true}";
        DisplayResponse response = MoshiUtil.fromJson(json, DisplayResponse.class);

        assertEquals(100, response.getNumberOfLines());
        assertEquals(20, response.getLineLength());
        assertEquals("display1", response.getDisplayText().get(0));
        assertEquals("display2", response.getDisplayText().get(1));
        assertEquals(true, response.isCancelKeyFlag());
        assertEquals(true, response.isAcceptYesKeyFlag());
        assertEquals(true, response.isDeclineNoKeyFlag());
        assertEquals(true, response.isAuthoriseKeyFlag());
        assertEquals(true, response.isOkKeyFlag());
        assertEquals(InputType.Decimal, response.getInputType());
        assertEquals(GraphicCode.Card, response.getGraphicCode());
    }

}
