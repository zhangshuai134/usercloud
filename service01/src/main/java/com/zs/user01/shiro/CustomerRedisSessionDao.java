package com.zs.user01.shiro;

import com.zs.user01.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class CustomerRedisSessionDao extends AbstractSessionDAO {

    @Autowired
    private RedisTemplate redisTemplate;

    //过期时间60分钟
    private long expire=60*60*1000;

    private final String keyPrefix="shiro_redis_session:";

    private RedisUtil getRedisUtilInstance() {
        return RedisUtil.getInstance(redisTemplate);
    }

    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId = generateSessionId(session);
        assignSessionId(session, sessionId);
        saveSession(session);
        return sessionId;
    }

    private String getKey(Serializable sessionId) {
        String preKey = this.keyPrefix + sessionId;
        return preKey;
    }


    private void saveSession(Session session) throws UnknownSessionException {

        if ((session == null) || (session.getId() == null)) {
            log.error("session or session id is null");
            return;
        }
            String key = getKey(session.getId());
            session.setTimeout(expire);
//            CustomerSession customerSession=new CustomerSession();
//            BeanUtils.copyProperties(session,customerSession);
            getRedisUtilInstance().set(key, session,expire, TimeUnit.MILLISECONDS.name());//this.redisManager.set(key, value, this.redisManager.getExpire());
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        if (sessionId == null) {
            log.error("session id is null");
            return null;
        }
        Session s = (Session) getRedisUtilInstance().get(getKey(sessionId));// SerializeUtils.deserialize(this.redisManager.get(getByteKey(sessionId)));
//        if(s instanceof CustomerSession){
//            SimpleSession simpleSession=new SimpleSession();
//            BeanUtils.copyProperties(s,simpleSession);
//            return simpleSession;
//
//        }else if(s instanceof SimpleSession){
//            return  s;
//        }
        return s;

    }

    @Override
    public void update(Session session) throws UnknownSessionException {
        this.saveSession(session);
    }

    @Override
    public void delete(Session session) {
        if ((session == null) || (session.getId() == null)) {
            log.error("session or session id is null");
            return;
        }
        getRedisUtilInstance().delete(getKey(session.getId()));
    }

    @Override
    public Collection<Session> getActiveSessions() {
//        Set sessions = new HashSet();
////        Set<String> keys = (Set<String>)getRedisUtilInstance().hkeys(this.keyPrefix + "*");
////        if ((keys != null) && (keys.size() > 0)) {
////            for (String key : keys) {
////                Session s = (Session) getRedisUtilInstance().get(key);// (Session) SerializeUtils.deserialize(this.redisManager.get(key));
////                sessions.add(s);
////            }
////        }
////        return sessions;
        return null;
    }
}
