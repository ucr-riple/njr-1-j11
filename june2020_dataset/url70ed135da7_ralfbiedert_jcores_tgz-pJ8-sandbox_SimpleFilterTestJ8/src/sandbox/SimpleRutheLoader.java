/*
 * SimpleSpeedTests.java
 * 
 * Copyright (c) 2010, Ralf Biedert All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without modification, are
 * permitted provided that the following conditions are met:
 * 
 * Redistributions of source code must retain the above copyright notice, this list of
 * conditions and the following disclaimer. Redistributions in binary form must reproduce the
 * above copyright notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * 
 * Neither the name of the author nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS
 * OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
 * MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE
 * COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR
 * TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE,
 * EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * 
 */
package sandbox;

import static net.jcores.jre.CoreKeeper.$;

import java.io.File;
import java.io.IOException;

import net.jcores.jre.interfaces.functions.F1;

/** */
public class SimpleRutheLoader {

    /**
     * @param args
     * @throws IOException 
     */
    public static void main(String[] args) throws IOException {
        $("dl/log.txt").file().delete();
        $.range(1, 1532).forEach(new F1<Number, Void>() {
            @Override
            public Void f(Number id) {
                // This is what we are looing for: </ul>  <p><strong> 3.8</strong>/5 (48 Stimmen)  </p>
                final String text = $("http://x/index.php?pic=" + id).uri().download().text().get(0);
                final int a = text.indexOf("strong");
                final int b = text.lastIndexOf("Stimmen");
                final int c = text.indexOf("<img src=\"cartoons/strip_") + "<img src=\"cartoons/strip_".length();
                
                // Extract rating
                final String fragment = text.substring(a, b);
                final String score = fragment.substring(8, 11);
                final String number = fragment.substring(24, fragment.length());
                final String trueimage = text.substring(c, c + 4);
                
                System.out.println("Grabbing " + id + " (filename " + trueimage + ")");
                
                // Download file
                final String filename = String.format("%04d", id);
                final File file = $("http://x/cartoons/strip_" + trueimage + ".jpg").uri().download("dl").get(0);
                if(file == null) return null;
                
                file.renameTo(new File("dl/" + filename + " (" + score + ").jpg"));
                
                
                // Append statistics
                $("dl/log.txt").file().append(filename + " " + trueimage + " " + score + " " + number + "\n");
                
                return null;
            }
        });
    }
}
