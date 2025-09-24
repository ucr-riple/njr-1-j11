package org.vit.unsafeutils.tcp;

import org.apache.log4j.Logger;
import org.vit.unsafeutils.UnsafeAccess;

import java.nio.ByteBuffer;

/*
Copyright 2014 Vitaly Maryin

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/
public class TcpReceiveBuffer {
    private static final Logger logger = Logger.getLogger(TcpReceiveBuffer.class);
    private ByteBuffer readBuffer;
    private int capacity = 52428800;

    public TcpReceiveBuffer(int bufSize){
        this.capacity = bufSize;
        readBuffer = ByteBuffer.wrap(new byte[this.capacity]);
    }

    public ByteBuffer getReadBuffer() {
        return readBuffer;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public boolean atEnd() {
        if (readBuffer.position() <= UnsafeAccess.TCP_DELIMITER.length  || readBuffer.position() % 8 != 0)
            return false;
        else
            return readBuffer.array()[readBuffer.position()-1] == UnsafeAccess.TCP_DELIMITER[UnsafeAccess.TCP_DELIMITER.length-1]
                    && readBuffer.array()[readBuffer.position()-2] == UnsafeAccess.TCP_DELIMITER[UnsafeAccess.TCP_DELIMITER.length-2]
                    && readBuffer.array()[readBuffer.position()-3] == UnsafeAccess.TCP_DELIMITER[UnsafeAccess.TCP_DELIMITER.length-3]
                    && readBuffer.array()[readBuffer.position()-4] == UnsafeAccess.TCP_DELIMITER[UnsafeAccess.TCP_DELIMITER.length-4]
                    && readBuffer.array()[readBuffer.position()-5] == UnsafeAccess.TCP_DELIMITER[UnsafeAccess.TCP_DELIMITER.length-5]
                    && readBuffer.array()[readBuffer.position()-6] == UnsafeAccess.TCP_DELIMITER[UnsafeAccess.TCP_DELIMITER.length-6]
                    && readBuffer.array()[readBuffer.position()-7] == UnsafeAccess.TCP_DELIMITER[UnsafeAccess.TCP_DELIMITER.length-7]
                    && readBuffer.array()[readBuffer.position()-8] == UnsafeAccess.TCP_DELIMITER[UnsafeAccess.TCP_DELIMITER.length-8];
    }
}
