package org.vsg.test;

import org.rapidoid.annotation.Controller;
import org.rapidoid.annotation.Page;

@Controller
public class Simple {
 
    @Page(raw = true)
    public Object simple() {
        return "<p><b>RAW</b> HTML!<p>";
    }
 
}