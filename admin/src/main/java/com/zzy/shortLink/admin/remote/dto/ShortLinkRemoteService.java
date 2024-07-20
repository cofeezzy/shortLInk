package com.zzy.shortLink.admin.remote.dto;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.TypeReference;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zzy.shortLink.admin.common.convention.result.Result;
import com.zzy.shortLink.admin.dto.req.RecycleBinSaveDTO;
import com.zzy.shortLink.admin.remote.dto.req.*;
import com.zzy.shortLink.admin.remote.dto.resp.ShortLinkCreateRespDTO;
import com.zzy.shortLink.admin.remote.dto.resp.ShortLinkGroupCountQueryRespDTO;
import com.zzy.shortLink.admin.remote.dto.resp.ShortLinkPageRespDTO;
import com.zzy.shortLink.admin.remote.dto.resp.ShortLinkStatsRespDTO;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 短链接中台远程调用服务
 */
public interface ShortLinkRemoteService {

    /**
     * 创建短链接
     * @param shortLinkCreateReqDTO 创建短链接请求参数
     * @return
     */
    default Result<ShortLinkCreateRespDTO> createShortLink(ShortLinkCreateReqDTO shortLinkCreateReqDTO){
        String resultBodyStr = HttpUtil.post("http://127.0.0.1:8001/api/short-link/v1/create", JSON.toJSONString(shortLinkCreateReqDTO));
        return JSON.parseObject(resultBodyStr, new TypeReference<>() {
        });
    }

    /**
     * 修改短链接
     * @param reqDTO 修改短链接请求参数
     * @return
     */
    default void updateShortLink(ShortLinkUpdateReqDTO reqDTO){
       HttpUtil.post("http://127.0.0.1:8001/api/short-link/v1/update", JSON.toJSONString(reqDTO));
    };

    /**
     * 分页查询短链接
     * @param shortLinkPageReqDTO 分页查询短链接请求参数
     * @return
     */
    default Result<IPage<ShortLinkPageRespDTO>> pageShortLink(ShortLinkPageReqDTO shortLinkPageReqDTO){
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("gid", shortLinkPageReqDTO.getGid());
        requestMap.put("current", shortLinkPageReqDTO.getCurrent());
        requestMap.put("size", shortLinkPageReqDTO.getSize());
        String resultPageStr = HttpUtil.get("http://127.0.0.1:8001/api/short-link/v1/page", requestMap);
        return JSON.parseObject(resultPageStr, new TypeReference<>(){
        });
    }

    /**
     * 查询分组短链接总量
     * @param requestParam 分页查询短链接请求参数
     * @return
     */
    default Result<List<ShortLinkGroupCountQueryRespDTO>> listGroupShortLinkCount(List<String> requestParam){
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("requestParam", requestParam);
        String resultPageStr = HttpUtil.get("http://127.0.0.1:8001/api/short-link/v1/count", requestMap);
        return JSON.parseObject(resultPageStr, new TypeReference<>(){
        });
    }

    /**
     *
     * @param url 目标网站地址
     * @return 网站标题
     */
    default Result<String> getTitleByUrl(@RequestParam("url") String url) {
        String resultStr = HttpUtil.get("http://127.0.0.1:8001/api/short-link/v1/title?url=" + url);
        return JSON.parseObject(resultStr, new TypeReference<>() {
        });
    }

    /**
     *  回收站保存
     * @param recycleBinSaveDTO
     */
    default void saveRecycleBin(RecycleBinSaveDTO recycleBinSaveDTO){
        HttpUtil.post("http://127.0.0.1:8001/api/short-link/v1/recycle-bin/save", JSON.toJSONString(recycleBinSaveDTO));
    }

    /**
     * 分页查询回收站短链接
     * @param shortLinkRecycleBinPageReqDTO 分页查询短链接请求参数
     * @return
     */
    default Result<IPage<ShortLinkPageRespDTO>> pageRecycleBinShortLink(ShortLinkRecycleBinPageReqDTO shortLinkRecycleBinPageReqDTO){
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("gidList", shortLinkRecycleBinPageReqDTO.getGidList());
        requestMap.put("current", shortLinkRecycleBinPageReqDTO.getCurrent());
        requestMap.put("size", shortLinkRecycleBinPageReqDTO.getSize());
        String resultPageStr = HttpUtil.get("http://127.0.0.1:8001/api/short-link/v1/recycle-bin/page", requestMap);
        return JSON.parseObject(resultPageStr, new TypeReference<>(){
        });
    }

    /**
     * 恢复短链接
     * @param recycleBinRecoverDTO 短链接恢复请求参数
     */
    default void recoverRecycleBin(RecycleBinRecoverDTO recycleBinRecoverDTO){
        HttpUtil.post("http://127.0.0.1:8001/api/short-link/v1/recycle-bin/recover", JSON.toJSONString(recycleBinRecoverDTO));
    };

    /**
     * 从回收站移除短链接
     * @param recycleBinRemoveDTO
     */
    default void removeRecycleBin(RecycleBinRemoveDTO recycleBinRemoveDTO){
        HttpUtil.post("http://127.0.0.1:8001/api/short-link/v1/recycle-bin/remove", JSON.toJSONString(recycleBinRemoveDTO));
    };

    /**
     *  访问单个短链接指定时间内监控数据
     * @param shortLinkStatsReqDTO
     * @return
     */
    default Result<ShortLinkStatsRespDTO> oneShortLinkStats(ShortLinkStatsReqDTO shortLinkStatsReqDTO){
        String resultBodyStr = HttpUtil.get("http://127.0.0.1:8001/api/short-link/v1/stats", BeanUtil.beanToMap(shortLinkStatsReqDTO));
        return JSON.parseObject(resultBodyStr, new TypeReference<>() {
        });
    }
}
