//package com.truong.common.exception;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.jdbc.datasource.DataSourceUtils;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.support.TransactionSynchronizationManager;
//import org.springframework.web.servlet.HandlerInterceptor;
//import org.springframework.web.servlet.ModelAndView;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.sql.DataSource;
//import java.sql.SQLException;
//import java.sql.Connection;
//
//
//@Component
//public class DataSourceLoggingInterceptor implements HandlerInterceptor {
//
//    private static final Logger logger = LoggerFactory.getLogger(DataSourceLoggingInterceptor.class);
//
//    @Autowired
//    private DataSource dataSource;
//
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws SQLException {
//		Connection connection = dataSource.getConnection();
//
//		try {
//		    String currentDataSourceUrl = connection.getMetaData().getURL();
//		    System.out.print("API Request - Current DataSource URL: " + currentDataSourceUrl);
//		} catch (Exception e) {
//		    // Xử lý exception nếu cần
//		} finally {
//		    DataSourceUtils.releaseConnection(connection, dataSource);
//		}
//		return true;
//    }
//
//
//    @Override
//    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
//        // No action needed in this example
//    }
//
//    @Override
//    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
//        // No action needed in this example
//    }
//}
//
