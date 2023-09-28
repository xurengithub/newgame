package com.xuren.game.common.transaction;

import java.util.List;

/**
 * @author xuren
 */
public class TransactionContext {
    private List<UndoLog> undoLogList;

    public boolean addLog(UndoLog log) {
        return undoLogList.add(log);
    }

    public void rollback() {
        // 找到现有的数据路径，反向重做undoLog
    }
}
