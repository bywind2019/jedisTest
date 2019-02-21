package com.atguigu.sevlert;

import java.io.IOException;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class SecKillServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
    public SecKillServlet() {
        super();
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//System.out.println("进入servlet处理请求");
		//模拟生成一个用户id
		String userid=new Random().nextInt(50000)+"";
		
		String prodid = request.getParameter("prodid");
		
		//System.out.println(userid +" ==>"+prodid);
		//通过秒杀方法判断该用户是否秒杀成功
		//boolean if_success = new SecKill_redis().doSeckill(userid, prodid);
		//通过Lua脚本判断秒杀
		boolean if_success = new SecKill_redisByScript().doSecKill(userid, prodid);
		response.getWriter().print(if_success);
		
	}
	

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
