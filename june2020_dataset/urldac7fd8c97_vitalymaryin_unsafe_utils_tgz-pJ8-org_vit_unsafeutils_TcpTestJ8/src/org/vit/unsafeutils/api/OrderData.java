package org.vit.unsafeutils.api;

import org.vit.unsafeutils.serializer.UnsafeBuffer;

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
public class OrderData implements UnsafeSerializable {
    private String ric;
    private double last;

    public String getRic() {
        return ric;
    }

    public void setRic(String ric) {
        this.ric = ric;
    }

    public double getLast() {
        return last;
    }

    public void setLast(double last) {
        this.last = last;
    }

    @Override
    public void write(UnsafeBuffer data) {
        data.putInt(getClass().getName().hashCode());
        data.putString(ric);
        data.putDouble(last);

    }

    @Override
    public UnsafeSerializable read(UnsafeBuffer data) {
        String ric = data.getString();
        double last = data.getDouble();
        byte[] tmp = data.getByteArray();
        OrderData ret = new OrderData();
        ret.setRic(ric);
        ret.setLast(last);
        return ret;
    }

    @Override
    public int getDefaultSize() {
        return 128;
    }

    @Override
    public String getObjectId() {
        return null;
    }
}
