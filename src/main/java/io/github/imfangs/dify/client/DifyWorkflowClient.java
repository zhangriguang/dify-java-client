package io.github.imfangs.dify.client;

import io.github.imfangs.dify.client.callback.WorkflowStreamCallback;
import io.github.imfangs.dify.client.exception.DifyApiException;
import io.github.imfangs.dify.client.model.workflow.*;

import java.io.IOException;

/**
 * Dify Workflow应用客户端接口
 * 包含Workflow应用相关的功能
 */
public interface DifyWorkflowClient extends DifyBaseClient {

    /**
     * 执行工作流（阻塞模式）
     *
     * @param request 请求
     * @return 响应
     * @throws IOException IO异常
     * @throws DifyApiException API异常
     */
    WorkflowRunResponse runWorkflow(WorkflowRunRequest request) throws IOException, DifyApiException;

    /**
     * 执行工作流（流式模式）
     *
     * @param request  请求
     * @param callback 回调
     * @throws IOException IO异常
     * @throws DifyApiException API异常
     */
    void runWorkflowStream(WorkflowRunRequest request, WorkflowStreamCallback callback) throws IOException, DifyApiException;

    /**
     * 停止工作流
     *
     * @param taskId 任务 ID
     * @param user   用户标识
     * @return 响应
     * @throws IOException IO异常
     * @throws DifyApiException API异常
     */
    WorkflowStopResponse stopWorkflow(String taskId, String user) throws IOException, DifyApiException;

    /**
     * 获取工作流运行状态
     *
     * @param workflowRunId 工作流运行实例 ID（从工作流执行响应中获得的运行实例标识）
     * @return 工作流执行状态响应
     * @throws IOException IO异常
     * @throws DifyApiException API异常
     */
    WorkflowRunStatusResponse getWorkflowRun(String workflowRunId) throws IOException, DifyApiException;

    /**
     * 获取工作流日志
     *
     * @param keyword 关键字
     * @param status  状态
     * @param page    页码
     * @param limit   每页条数
     * @return 日志列表
     * @throws IOException IO异常
     * @throws DifyApiException API异常
     */
    WorkflowLogsResponse getWorkflowLogs(String keyword, String status, Integer page, Integer limit) throws IOException, DifyApiException;
}
