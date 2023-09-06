package com.linkly.pos.sdk.adapters.enums;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import com.linkly.pos.sdk.common.MoshiUtil;
import com.linkly.pos.sdk.models.enums.GraphicCode;
import com.squareup.moshi.JsonAdapter;

class GraphicCodeAdapterTest {

    private JsonAdapter<TestClass> adapter = MoshiUtil.getAdapter(TestClass.class);

    @Test
    void should_deserialize_graphicCodeEnum() {
        TestClass testClass = new TestClass();
        String content = adapter.toJson(testClass);
        assertEquals("{\"type1\":\"0\",\"type2\":\"1\",\"type3\":\"2\",\"type4\":\"3\",\"type5\":"
            + "\"4\",\"type6\":\"5\",\"type7\":\"6\",\"type8\":\" \",\"type9\":\"Unknown\"}",
            content);
    }

    @Test
    void should_serialize_graphicCodeEnum() throws IOException {
        String content =
            "{\"type1\":\"0\",\"type2\":\"1\",\"type3\":\"2\",\"type4\":\"3\",\"type5\":"
                + "\"4\",\"type6\":\"5\",\"type7\":\"6\",\"type8\":\" \",\"type9\":\"Unknown\"}";
        TestClass testClass = adapter.fromJson(content);
        assertTrue(testClass.getType1().equals(GraphicCode.Processing));
        assertTrue(testClass.getType2().equals(GraphicCode.Verify));
        assertTrue(testClass.getType3().equals(GraphicCode.Question));
        assertTrue(testClass.getType4().equals(GraphicCode.Card));
        assertTrue(testClass.getType5().equals(GraphicCode.Account));
        assertTrue(testClass.getType6().equals(GraphicCode.PIN));
        assertTrue(testClass.getType7().equals(GraphicCode.Finished));
        assertTrue(testClass.getType8().equals(GraphicCode.None));
        assertTrue(testClass.getType9().equals(GraphicCode.Unknown));
    }

    private static class TestClass {
        private GraphicCode type1 = GraphicCode.Processing;
        private GraphicCode type2 = GraphicCode.Verify;
        private GraphicCode type3 = GraphicCode.Question;
        private GraphicCode type4 = GraphicCode.Card;
        private GraphicCode type5 = GraphicCode.Account;
        private GraphicCode type6 = GraphicCode.PIN;
        private GraphicCode type7 = GraphicCode.Finished;
        private GraphicCode type8 = GraphicCode.None;
        private GraphicCode type9 = GraphicCode.Unknown;

        public GraphicCode getType1() {
            return type1;
        }

        public GraphicCode getType2() {
            return type2;
        }

        public GraphicCode getType3() {
            return type3;
        }

        public GraphicCode getType4() {
            return type4;
        }

        public GraphicCode getType5() {
            return type5;
        }

        public GraphicCode getType6() {
            return type6;
        }

        public GraphicCode getType7() {
            return type7;
        }

        public GraphicCode getType8() {
            return type8;
        }

        public GraphicCode getType9() {
            return type9;
        }

    }

}
