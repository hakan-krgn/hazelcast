/*
 * Copyright (c) 2008-2024, Hazelcast, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hazelcast.client.impl.protocol.codec.builtin;

import com.hazelcast.client.impl.protocol.ClientMessage;

import static com.hazelcast.client.impl.protocol.codec.builtin.FixedSizeTypesCodec.FLOAT_SIZE_IN_BYTES;
import static com.hazelcast.client.impl.protocol.codec.builtin.FixedSizeTypesCodec.decodeFloat;
import static com.hazelcast.client.impl.protocol.codec.builtin.FixedSizeTypesCodec.encodeFloat;

public final class FloatArrayCodec {

    private FloatArrayCodec() {
    }

    public static void encode(ClientMessage clientMessage, float[] array) {
        int itemCount = array.length;
        ClientMessage.Frame frame = new ClientMessage.Frame(new byte[itemCount * FLOAT_SIZE_IN_BYTES]);
        for (int i = 0; i < itemCount; i++) {
            encodeFloat(frame.content, i * FLOAT_SIZE_IN_BYTES, array[i]);
        }
        clientMessage.add(frame);
    }

    public static float[] decode(ClientMessage.Frame frame) {
        int itemCount = frame.content.length / FLOAT_SIZE_IN_BYTES;
        float[] result = new float[itemCount];
        for (int i = 0; i < itemCount; i++) {
            result[i] = decodeFloat(frame.content, i * FLOAT_SIZE_IN_BYTES);
        }
        return result;
    }

    public static float[] decode(ClientMessage.ForwardFrameIterator iterator) {
        return decode(iterator.next());
    }

}
