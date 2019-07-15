package com.vmet;

import com.vmet.BaseApplication.BaseApplication;


class Chatting_With_Who {


        private static String getSender ;
        private static String getReceiver ;

        public Chatting_With_Who() {
        }

        public static String getGetSender() {
                return getSender;
        }

        public static void setGetSender(String getSender) {
                Chatting_With_Who.getSender = getSender;
        }

        public static String getGetReceiver() {
                return getReceiver;
        }

        public static void setGetReceiver(String getReceiver) {
                Chatting_With_Who.getReceiver = getReceiver;
        }
}
