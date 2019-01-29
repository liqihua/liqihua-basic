package com.liqihua.config.shiro;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author liqihua
 * @since 2019/1/29
 */
public class SysSessionDAO extends AbstractSessionDAO {
    private static final Logger LOG = LoggerFactory.getLogger(SysSessionDAO.class);
    private static final String KEY_PREFIX = "shiro-session:";

    private RedissonClient redissonClient;

    public SysSessionDAO() {
    }

    public SysSessionDAO(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    @Override
    protected Serializable doCreate(Session session) {
        LOG.info("创建session...");
        Serializable sessionId = this.generateSessionId(session);
        this.assignSessionId(session, sessionId);
        this.saveSession(session);
        LOG.info("session："+session.getId());
        return sessionId;
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        LOG.info("读取session："+sessionId);
        if (sessionId == null) {
            LOG.error("session id is null");
            return null;
        } else {
            RBucket<Session> rs = this.redissonClient.getBucket(KEY_PREFIX + sessionId);
            Session session = (Session)rs.get();
            return session;
        }
    }

    @Override
    public void update(Session session) throws UnknownSessionException {
        LOG.info("更新session:{}", session.getId());
        this.saveSession(session);
    }

    @Override
    public void delete(Session session) {
        LOG.info("删除session:{}",session.getId());
        if (session != null && session.getId() != null) {
            RBucket<Session> rs = this.redissonClient.getBucket(KEY_PREFIX + session.getId());
            rs.delete();
        } else {
            LOG.error("session or session id is null");
        }
    }

    @Override
    public Collection<Session> getActiveSessions() {
        Set<Session> sessions = new HashSet(0);
        return sessions;
    }

    private void saveSession(Session session) throws UnknownSessionException {
        if (session != null && session.getId() != null) {
            RBucket<Session> rs = this.redissonClient.getBucket(KEY_PREFIX + session.getId());
            rs.set(session);
            rs.expire(30L, TimeUnit.MINUTES);
        } else {
            LOG.error("session or session id is null");
        }
    }

}
