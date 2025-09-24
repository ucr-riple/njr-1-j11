/**
 * http://auzll.iteye.com/
 */
package com.gengweibo.weibo;

import static com.gengweibo.weibo.Weibo.Method.GET;
import static com.gengweibo.weibo.Weibo.Method.POST;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.oauth.OAuth;
import net.oauth.OAuth.Parameter;
import net.oauth.OAuthAccessor;
import net.oauth.OAuthConsumer;
import net.oauth.OAuthMessage;
import net.oauth.OAuthServiceProvider;
import net.oauth.ParameterStyle;
import net.oauth.client.OAuthClient;
import net.oauth.client.URLConnectionClient;
import net.oauth.http.HttpMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.inject.internal.Lists;
import com.google.inject.internal.Maps;

/**
 * 微博接口的抽象实现。本实现是基于网易微博的，也就是说Weibo163这个类基本不用修改就能 使用；其余的WeiboQQ等等实现类需要做一些调整才能使用。
 * 
 * @author auzll@msn.com
 * @since 2011-3-11
 */
public abstract class Weibo implements IWeibo, Serializable {
    private static final Log LOG = LogFactory.getLog(Weibo.class);

    /**
     * OAuth请求类型，本系统暂时只需要用到GET或POST
     */
    public static enum Method {
        GET, POST;
    }

    private static final long serialVersionUID = -5519577190711442534L;

    /** 分页参数，每页20条，每次最多可以获取20条数据 */
    protected static final int PAGE_MAX = 20;

    protected OAuthAccessor accessor;

    /** 用户在本系统中的id，如果是初次使用，用UUID生成 */
    protected String accountId;

    /** 微博类型 */
    protected WeiboType type;

    /** 各微博厂商请求资源的地址 */
    protected String urlResource;

    /** 用户在微博厂商系统中的昵称 */
    protected String weiboAccountName;

    /**
     * 微博账户在本系统中的ID，注意区别微博内容的id，本系统用statusId来表示微博内容的id。<br/>
     * 微博账户ID格式(type_userIdorUserName): T_163_123123, T_QQ_zzzz, T_SINA_1231235
     */
    protected String weiboId;

    protected boolean synUpdate;

    protected int parseLegalPageCount(String value) {
        if (null != value) {
            try {
                int i = Integer.valueOf(value.trim());
                if (i > 0 && i < PAGE_MAX) {
                    return i;
                }
            } catch (NumberFormatException e) {}
        }

        return PAGE_MAX;
    }

    public Weibo(String urlRequest, String urlAuthorization, String urlAccess,
            String urlResource, String urlCallback, String consumerKey,
            String consumerSecret, WeiboType type) {
        OAuthServiceProvider provider = new OAuthServiceProvider(urlRequest,
                urlAuthorization, urlAccess);
        OAuthConsumer consumer = new OAuthConsumer(urlCallback, consumerKey,
                consumerSecret, provider);
        this.accessor = new OAuthAccessor(consumer);
        this.urlResource = urlResource;
        if (!this.urlResource.endsWith("/")) {
            this.urlResource += "/";
        }
        this.type = type;
    }

    public void bindWeiboAccountContext() {
        if (null == weiboId && null == weiboAccountName) {
            Response response = verifyCredentials();
            try {
                JSONObject jsonObject = new JSONObject(response.readBodyAsString());
                weiboId = getType().getEnName() + "_" + jsonObject.getString("id");
                weiboAccountName = jsonObject.getString("name");
            } catch (JSONException e) {
                throw new WeiException(e);
            }
        }
    }

    public void convertAccessToken(IParam param) throws WeiException {
        String oauth_verifier = param.getParamValue("oauth_verifier");
        if (null == accessor.requestToken) {
            throw new NullPointerException("accessor.requestToken is null");
        }
        Map<String, String> map = Maps.newHashMap();
        map.put("oauth_token", accessor.requestToken);
        if (null != oauth_verifier) {
            map.put("oauth_verifier", oauth_verifier);
        }
        Response response = sendRequest(map,
                accessor.consumer.serviceProvider.accessTokenURL, GET);
        accessor.requestToken = null;
        accessor.accessToken = response.getParameter("oauth_token");
        accessor.tokenSecret = response.getParameter("oauth_token_secret");
    }

    public Response favoritesCreate(IParam param) {
        String id = param.getParamValue("statusId");
        return sendRequest(null, urlResource + "favorites/create/" + id
                + ".json", POST);
    }

    public Response friendshipsCreate(IParam param) {
        String userId = param.getParamValue("userId");
        return sendRequest(toRequestParam("user_id", userId), urlResource
                + "friendships/create.json", POST);
    }

    public Response friendshipsDestroy(IParam param) {
        String userId = param.getParamValue("userId");
        return sendRequest(toRequestParam("user_id", userId), urlResource
                + "friendships/destroy.json", POST);
    }

    public String getAccountId() {
        return accountId;
    }

    public WeiboType getType() {
        return type;
    }

    public String getWeiboAccountName() {
        return weiboAccountName;
    }

    public String getWeiboId() {
        return weiboId;
    }

    public Response homeTimeline(IParam param) {
        RequestParam reqParam = toRequestParam("count",
                parseLegalPageCount(param.getParamValue("count")));

        if (null != param.getParamValue("since_id")) {
            reqParam.add("since_id", param.getParamValue("since_id"));
        }

        if (null != param.getParamValue("max_id")) {
            reqParam.add("max_id", param.getParamValue("max_id"));
        }

        return sendRequest(reqParam, urlResource
                + "statuses/home_timeline.json", GET);
    }

    public String makeClickUrl() throws WeiException {
        OAuthClient client = new OAuthClient(new URLConnectionClient());
        try {
            client.getRequestToken(accessor);
        } catch (Exception e) {
            throw new WeiException(e);
        }

        Collection<Parameter> parameters = Lists.newArrayList();
        parameters.add(new Parameter("oauth_token", accessor.requestToken));
        if (null != accessor.consumer.callbackURL) {
            parameters.add(new Parameter("oauth_callback",
                    accessor.consumer.callbackURL));
        }

        try {
            OAuthMessage request = accessor.newRequestMessage(GET.toString(),
                    accessor.consumer.serviceProvider.userAuthorizationURL,
                    parameters);
            Object accepted = accessor.consumer
                    .getProperty(OAuthConsumer.ACCEPT_ENCODING);
            if (accepted != null) {
                request.getHeaders().add(
                        new OAuth.Parameter(HttpMessage.ACCEPT_ENCODING,
                                accepted.toString()));
            }
            Object ps = accessor.consumer
                    .getProperty(OAuthClient.PARAMETER_STYLE);
            ParameterStyle style = (ps == null) ? ParameterStyle.BODY : Enum
                    .valueOf(ParameterStyle.class, ps.toString());
            HttpMessage httpRequest = HttpMessage.newRequest(request, style);
            return httpRequest.url.toString();
        } catch (Exception e) {
            throw new WeiException(e);
        }
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public void setWeiboAccountName(String accountName) {
        this.weiboAccountName = accountName;
    }

    public void setWeiboId(String weiboId) {
        this.weiboId = weiboId;
    }

    public Response statusesComments(IParam param) {
        RequestParam reqParam = toRequestParam("count", PAGE_MAX);
        if (null != param.getParamValue("since_id")) {
            // 163可选参数，该参数需传微博id，返回此条索引之后发的微博列表，不包含此条；
            reqParam.add("since_id", param.getParamValue("since_id"));
        }
        return sendRequest(
                reqParam,
                urlResource + "statuses/comments/"
                        + param.getParamValue("statusId") + ".json", GET);
    }

    public Response statusesDestroy(IParam param) {
        String id = param.getParamValue("statusId");
        return sendRequest(null, urlResource + "statuses/destroy/" + id
                + ".json", POST);
    }

    public Response statusesReply(IParam param) {
        String id = param.getParamValue("statusId");
        String status = param.getParamValue("status");
        return sendRequest(toRequestParam("status", status).add("id", id),
                urlResource + "statuses/reply.json", POST);
    }

    public Response statusesRetweet(IParam param) {
        String id = param.getParamValue("statusId");

        RequestParam reqParam = null;
        if (null != param.getParamValue("status")) {
            reqParam = toRequestParam("status", param.getParamValue("status"));
        }

        return sendRequest(reqParam, urlResource + "statuses/retweet/" + id
                + ".json", POST);
    }

    public Response statusesUpdate(IParam param) {
        String status = param.getParamValue("status");
        return sendRequest(toRequestParam("status", status), urlResource + "statuses/update.json", POST);
    }

    public Response verifyCredentials() {
        return sendRequest(null, urlResource
                + "account/verify_credentials.json", GET);
    }

    protected static final int MAX_RETRY_TIMES = 3;

    protected Response sendRequest(Map<?, ?> map, String url, Method method)
            throws WeiException {
        try {
            List<Map.Entry<?, ?>> params = null;
            if (null != map) {
                params = Lists.newArrayList();
                Iterator<?> it = map.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry<?, ?> p = (Map.Entry<?, ?>) it.next();
                    params.add(new OAuth.Parameter((String) p.getKey(),
                            (String) p.getValue()));
                }
            }

            OAuthClient client = new OAuthClient(new URLConnectionClient());

            int tryTimes = 0;
            while (tryTimes < MAX_RETRY_TIMES) {
                try {
                    tryTimes++;
                    return new Response(client.invoke(accessor, method.toString(), url, params));
                } catch (Exception e) {
                    // 最多重试MAX_RETRY_TIMES
                    LOG.error("method:sendRequest,url:" + url + ",tryTimes:" + tryTimes);
                    if (tryTimes >= MAX_RETRY_TIMES) {
                        throw e;
                    }
                }
            }

            throw new RuntimeException("UnexpectedException");

        } catch (Exception e) {
            throw new WeiException(e);
        }
    }

    protected class RequestParam extends HashMap<String, String> {
        private static final long serialVersionUID = -5338614760105715587L;

        public RequestParam() {}

        public RequestParam(String key, Object value) {
            put(key, value.toString());
        }

        public RequestParam add(String key, Object value) {
            if (null != value) {
                put(key, value.toString());
            }
            return this;
        }
    }

    protected RequestParam toRequestParam(String key, Object value) {
        if (null == value) {
            throw new NullPointerException("value for key[" + key + "] is null");
        }
        return new RequestParam(key, value.toString());
    }

    public OAuthAccessor getAccessor() {
        return accessor;
    }

    public Response statusesMentions(IParam param) {
        RequestParam reqParam = toRequestParam("count",
                parseLegalPageCount(param.getParamValue("count")));

        if (null != param.getParamValue("since_id")) {
            reqParam.add("since_id", param.getParamValue("since_id"));
        }

        if (null != param.getParamValue("max_id")) {
            reqParam.add("max_id", param.getParamValue("max_id"));
        }

        return sendRequest(reqParam, urlResource + "statuses/mentions.json",
                GET);
    }

    public Response statusesCommentsToMe(IParam param) {
        RequestParam reqParam = toRequestParam("count",
                parseLegalPageCount(param.getParamValue("count")));

        if (null != param.getParamValue("since_id")) {
            reqParam.add("since_id", param.getParamValue("since_id"));
        }

        reqParam.add("trim_user", "false");

        return sendRequest(reqParam, urlResource
                + "statuses/comments_to_me.json", GET);
    }

    public boolean isSynUpdate() {
        return synUpdate;
    }

    public void setSynUpdate(boolean synUpdate) {
        this.synUpdate = synUpdate;
    }
}
