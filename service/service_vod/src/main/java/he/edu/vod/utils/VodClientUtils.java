package he.edu.vod.utils;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.req.UploadVideoRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyun.vod.upload.resp.UploadVideoResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.vod.model.v20170321.*;

import java.io.InputStream;

/**
 * 阿里云视频点播-单例
 *
 * @Author: aidawone
 * @Description:
 * @Date: Create in 16:35 2021/2/26
 */
public class VodClientUtils {

    /**
     * 初始化
     *
     * @return
     */
    public static DefaultAcsClient initVodClient() {
        String regionId = "cn-shanghai";  // 点播服务接入区域
        DefaultProfile profile = DefaultProfile.getProfile(regionId, ConstantVodUtils.ACCESS_KEY_ID, ConstantVodUtils.ACCESS_KEY_SECRET);
        DefaultAcsClient client = new DefaultAcsClient(profile);
        return client;
    }

    /*获取播放地址函数*/
    public static GetPlayInfoResponse getPlayInfo(DefaultAcsClient client, String videoId) throws Exception {
        GetPlayInfoRequest request = new GetPlayInfoRequest();
        request.setVideoId(videoId);
        return client.getAcsResponse(request);
    }

    /*获取播放凭证函数*/
    public static GetVideoPlayAuthResponse getVideoPlayAuth(DefaultAcsClient client, String videoId) throws Exception {
        GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
        request.setVideoId(videoId);
        return client.getAcsResponse(request);
    }

    /**
     * 删除视频
     */
    public static void deleteVideo(DefaultAcsClient client, String id) throws Exception {
        DeleteVideoRequest request = new DeleteVideoRequest();
        //支持传入多个视频ID，多个用逗号分隔
        request.setVideoIds(id);
        //调用初始化对象的方法实现删除
        client.getAcsResponse(request);
    }

    /**
     * 本地文件上传接口
     *
     * @param title    上传成功文件的名称
     * @param fileName 文件的原名称
     */
    private static void UploadVideo(String title, String fileName) {
        UploadVideoRequest request = new UploadVideoRequest(ConstantVodUtils.ACCESS_KEY_ID, ConstantVodUtils.ACCESS_KEY_SECRET, title, fileName);
        /*可指定分片上传时每个分片的大小，默认为1M字节*/
        request.setPartSize(1 * 1024 * 1024L);
        /*可指定分片上传时的并发线程数，默认为1（注：该配置会占用服务器CPU资源，需根据服务器情况指定）*/
        request.setTaskNum(1);
        /*是否开启断点续传，默认断点续传功能关闭。当网络不稳定或者程序崩溃时，再次发起相同上传请求，可以继续未完成的上传任务，适用于超时3000秒仍不能上传完成的大文件。
        注意: 断点续传开启后，会在上传过程中将上传位置写入本地磁盘文件，影响文件上传速度，请您根据实际情况选择是否开启*/
        request.setEnableCheckpoint(false);
        /*OSS慢请求日志打印超时时间，是指每个分片上传时间超过该阈值时会打印debug日志，如果想屏蔽此日志，请调整该阈值。单位: 毫秒，默认为300000毫秒*/
        //request.setSlowRequestsThreshold(300000L);
        /*可指定每个分片慢请求时打印日志的时间阈值，默认为300s*/
        //request.setSlowRequestsThreshold(300000L);
        /*是否使用默认水印（可选），指定模板组ID时，根据模板组配置确定是否使用默认水印*/
        //request.setIsShowWaterMark(true);
        /*自定义消息回调设置（可选），参数说明参考文档 https://help.aliyun.com/document_detail/86952.html#UserData*/
        // request.setUserData("{\"Extend\":{\"test\":\"www\",\"localId\":\"xxxx\"},\"MessageCallback\":{\"CallbackURL\":\"http://test.test.com\"}}");

        /*视频分类ID（可选）*/
        //request.setCateId(0);
        /*视频标签,多个用逗号分隔（可选）*/
        //request.setTags（"标签1,标签2"）;
        /*视频描述（可选）*/
        //request.setDescription("视频描述");
        /*封面图片（可选）*/
        //request.setCoverURL("http://cover.sample.com/sample.jpg");
        /*模板组ID（可选）*/
        //request.setTemplateGroupId("8c4792cbc8694*****d5330e56a33d");
        /*点播服务接入点*/
        //request.setApiRegionId("cn-shanghai");
        /*ECS部署区域，如果与点播存储（OSS）区域相同，则自动使用内网上传文件至存储*/
        // request.setEcsRegionId("cn-shanghai");
        /*存储区域（可选）*/
        //request.setStorageLocation("in-2017032*****18266-5sejdln9o.oss-cn-shanghai.aliyuncs.com");
        /*开启默认上传进度回调*/
        // request.setPrintProgress(true);
        /*设置自定义上传进度回调（必须继承 ProgressListener）*/
        // request.setProgressListener(new PutObjectProgressListener());
        UploadVideoImpl uploader = new UploadVideoImpl();
        UploadVideoResponse response = uploader.uploadVideo(request);
//        System.out.print("RequestId="response.getRequestId()"\n");  //请求视频点播服务的请求ID
        if (response.isSuccess()) {
            System.out.print("VideoId=" + response.getVideoId() + "\n");
        } else {
            /* 如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因 */
            System.out.print("VideoId=" + response.getVideoId() + "\n");
            System.out.print("ErrorCode=" + response.getCode() + "\n");
            System.out.print("ErrorMessage=" + response.getMessage() + "\n");
        }
    }

    /**
     * 流式上传接口
     * @param title
     * @param fileName
     * @param inputStream
     */
    public static String uploadStream(String title, String fileName, InputStream inputStream) {
        UploadStreamRequest request = new UploadStreamRequest(ConstantVodUtils.ACCESS_KEY_ID, ConstantVodUtils.ACCESS_KEY_SECRET, title, fileName, inputStream);
        /* 是否使用默认水印(可选)，指定模板组ID时，根据模板组配置确定是否使用默认水印*/
        //request.setShowWaterMark(true);
        /* 设置上传完成后的回调URL(可选)，建议通过点播控制台配置消息监听事件，参见文档 https://help.aliyun.com/document_detail/57029.html */
        //request.setCallback("http://callback.sample.com");
        /* 自定义消息回调设置，参数说明参考文档 https://help.aliyun.com/document_detail/86952.html#UserData */
        //request.setUserData(""{\"Extend\":{\"test\":\"www\",\"localId\":\"xxxx\"},\"MessageCallback\":{\"CallbackURL\":\"http://test.test.com\"}}"");
        /* 视频分类ID(可选) */
        //request.setCateId(0);
        /* 视频标签,多个用逗号分隔(可选) */
        //request.setTags("标签1,标签2");
        /* 视频描述(可选) */
        //request.setDescription("视频描述");
        /* 封面图片(可选) */
        //request.setCoverURL("http://cover.sample.com/sample.jpg");
        /* 模板组ID(可选) */
        //request.setTemplateGroupId("8c4792cbc8694e7084fd5330e56a33d");
        /* 工作流ID(可选) */
        //request.setWorkflowId("d4430d07361f0*be1339577859b0177b");
        /* 存储区域(可选) */
        //request.setStorageLocation("in-201703232118266-5sejdln9o.oss-cn-shanghai.aliyuncs.com");
        /* 开启默认上传进度回调 */
        // request.setPrintProgress(true);
        /* 设置自定义上传进度回调 (必须继承 VoDProgressListener) */
        // request.setProgressListener(new PutObjectProgressListener());
        /* 设置应用ID*/
        //request.setAppId("app-1000000");
        /* 点播服务接入点 */
        //request.setApiRegionId("cn-shanghai");
        /* ECS部署区域*/
        // request.setEcsRegionId("cn-shanghai");
        UploadVideoImpl uploader = new UploadVideoImpl();
        UploadStreamResponse response = uploader.uploadStream(request);
        if (response.isSuccess()) {
            return response.getVideoId();
        }
        return null;
    }
}
