package org.vsg.test;

import org.rapidoid.annotation.GET;
import org.rapidoid.annotation.POST;
import org.rapidoid.annotation.Param;
import org.rapidoid.http.Req;
import org.rapidoid.http.Resp;
import org.rapidoid.http.fast.On;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		On.req(new Object() {
		     @GET
		    public String upper(@Param("s") String s) {
		        return s.toUpperCase();
		    }
		     @POST
		    public String lower(Req req, Resp resp, @Param("x") String s) {
		        return s.toLowerCase();
		    }
		});

	}

}
