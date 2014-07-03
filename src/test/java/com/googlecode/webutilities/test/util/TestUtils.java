/*
 * Copyright 2010-2014 Rajendra Patil
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.googlecode.webutilities.test.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

public final class TestUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestUtils.class.getName());

    private TestUtils() {
    }

    public static String readContents(InputStream inputStream, String encoding) throws Exception {

        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder stringBuilder = new StringBuilder();
        int c;
        while ((c = reader.read()) != -1) {
            stringBuilder.append((char)c);
        }
        inputStream.close();
        return new String(stringBuilder.toString().getBytes(),encoding);

    }


    public static boolean compressedContentEquals(String left, String right) throws IOException {
        int ch, pos = 0;

        if(left == null && right == null){
            return true;
        }

        assert left != null;
        ByteArrayInputStream streamLeft = new ByteArrayInputStream(left.getBytes());
        ByteArrayInputStream streamRight = new ByteArrayInputStream(right.getBytes());

        while ((ch = streamLeft.read()) != -1) {
            int ch2 = streamRight.read();
            if (ch != ch2) {
                if(pos == 9){ //Ignore OS byte in GZIP header
                	LOGGER.info("Ignoring OS bit.... {} != {}", ch, ch2);
                    continue;
                }
                return false;
            }
            pos++;
        }
        int ch2 = streamRight.read();
        return (ch2 == -1);
    }
}
