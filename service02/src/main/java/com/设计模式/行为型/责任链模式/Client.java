package com.设计模式.行为型.责任链模式;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: curry.zhang
 * @Date: 2019/9/17 20:50
 * @Description: 使用 建造者模式，我们完全可以对客户端指定的处理节点对象进行自动链式组装，
 * 客户只需指定处理节点对象，其他任何事情都无需关心，
 * 并且客户指定的处理节点对象顺序不同，构造出来的链式结构也随之不同
 */
class Client {
    public static void main(String[] args) {
        Handler.Builder<String> builder = new Handler.Builder<String>();
        List<Handler<String>> handlers = makeHanlders();
        for (Handler<String> handler : handlers) {
            builder.addHandler(handler);
        }
        Handler<String> firstHandler = builder.build();
        for (int i = 0; i < 25; ++i) {
            System.out.println("-----------------");
            Request<String> request = makeRequest(String.format("%03d", i));
            firstHandler.handleRequest(request);
        }
    }

    private static List<Handler<String>> makeHanlders() {
        List<Handler<String>> handlers = new ArrayList<>();
        for (int i = 0; i < 20; ++i) {
            String request = String.format("%03d", i);
            handlers.add(makeHandler("handler" + request, makeRequest(request)));
        }
        return handlers;
    }

    private static Request<String> makeRequest(String request) {
        return new Request<String>(request) {
            @Override
            public boolean isSame(Request<String> request) {
                return this.getRequest().equals(request.getRequest());
            }
        };
    }

    private static Handler<String> makeHandler(final String handlerName, Request<String> request) {
        return new Handler<String>(request) {
            @Override
            protected void handle(Request<String> request) {
                System.out.println(String.format("%s deal with request: %s", handlerName, request));
            }
        };
    }

    // 抽象消息
    static abstract class Request<T> {
        private T mRequest;

        public Request(T request) {
            this.mRequest = request;
        }

        public T getRequest() {
            return this.mRequest;
        }

        @Override
        public String toString() {
            return this.mRequest.toString();
        }

        public abstract boolean isSame(Request<T> request);
    }

    // 抽象处理者
    static abstract class Handler<T> {
        private Handler<T> mNextHandler;
        private Request<T> mRequest;

        public Handler(Request<T> request) {
            this.mRequest = request;
        }

        public void setNextHandler(Handler<T> successor) {
            this.mNextHandler = successor;
        }

        public final void handleRequest(Request<T> request) {
            do {
                if (this.mRequest.isSame(request)) {
                    this.handle(request);
                    break;
                }
                if (this.mNextHandler != null) {
                    this.mNextHandler.handleRequest(request);
                    break;
                }
                System.out.println("No Handlers can handle this request: " + request);
            } while (false);
        }

        protected abstract void handle(Request<T> request);

        public static class Builder<T> {
            private Handler<T> mFirst;
            private Handler<T> mLast;

            public Builder<T> addHandler(Handler<T> handler) {
                do {
                    if (this.mFirst == null) {
                        this.mFirst = this.mLast = handler;
                        break;
                    }
                    this.mLast.setNextHandler(handler);
                    this.mLast = handler;

                } while (false);
                return this;
            }

            public Handler<T> build() {
                return this.mFirst;
            }
        }

    }
}
