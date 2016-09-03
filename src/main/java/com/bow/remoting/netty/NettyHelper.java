/*
 * Copyright 1999-2011 Alibaba Group.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.bow.remoting.netty;

import org.jboss.netty.logging.AbstractInternalLogger;
import org.jboss.netty.logging.InternalLogger;
import org.jboss.netty.logging.InternalLoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


final class NettyHelper {

    public static void setNettyLoggerFactory() {
        InternalLoggerFactory factory = InternalLoggerFactory.getDefaultFactory();
        if (factory == null || !(factory instanceof ShineLoggerFactory)) {
            InternalLoggerFactory.setDefaultFactory(new ShineLoggerFactory());
        }
    }

    static class ShineLoggerFactory extends InternalLoggerFactory {

        @Override
        public InternalLogger newInstance(String name) {
            return new ShineLogger(LoggerFactory.getLogger(name));
        }
    }

    static class ShineLogger extends AbstractInternalLogger {

        private Logger logger;

        ShineLogger(Logger logger) {
            this.logger = logger;
        }

        public boolean isDebugEnabled() {
            return logger.isDebugEnabled();
        }

        public boolean isInfoEnabled() {
            return logger.isInfoEnabled();
        }

        public boolean isWarnEnabled() {
            return logger.isWarnEnabled();
        }

        public boolean isErrorEnabled() {
            return logger.isErrorEnabled();
        }

        public void debug(String msg) {
            logger.debug(msg);
        }

        public void debug(String msg, Throwable cause) {
            logger.debug(msg, cause);
        }

        public void info(String msg) {
            logger.info(msg);
        }

        public void info(String msg, Throwable cause) {
            logger.info(msg, cause);
        }

        public void warn(String msg) {
            logger.warn(msg);
        }

        public void warn(String msg, Throwable cause) {
            logger.warn(msg, cause);
        }

        public void error(String msg) {
            logger.error(msg);
        }

        public void error(String msg, Throwable cause) {
            logger.error(msg, cause);
        }

        @Override
        public String toString() {
            return logger.toString();
        }
    }

}
