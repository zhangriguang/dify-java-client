## Changes

- Optimize streaming response handling by introducing terminal event constants for different app types (#161)
  - Chat/Completion streams now terminate on `message_end` or `error`
  - Chatflow/Workflow streams now terminate on `workflow_finished` or `error`
  - Fixes issue where Chatflow events after `message_end` were being lost
- Add missing fields to `DetailedDocumentResponse`: `doc_type`, `doc_metadata`, `summary_index_status`, `need_summary` (#160)

