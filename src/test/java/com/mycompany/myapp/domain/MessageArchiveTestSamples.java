package com.mycompany.myapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class MessageArchiveTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static MessageArchive getMessageArchiveSample1() {
        return new MessageArchive()
            .id(1L)
            .message("message1")
            .senderRefTable("senderRefTable1")
            .senderRefId(1L)
            .receiverRefTable("receiverRefTable1")
            .receiverRefId(1L)
            .status("status1")
            .studentId(1L)
            .schoolId(1L)
            .lastModifiedBy("lastModifiedBy1");
    }

    public static MessageArchive getMessageArchiveSample2() {
        return new MessageArchive()
            .id(2L)
            .message("message2")
            .senderRefTable("senderRefTable2")
            .senderRefId(2L)
            .receiverRefTable("receiverRefTable2")
            .receiverRefId(2L)
            .status("status2")
            .studentId(2L)
            .schoolId(2L)
            .lastModifiedBy("lastModifiedBy2");
    }

    public static MessageArchive getMessageArchiveRandomSampleGenerator() {
        return new MessageArchive()
            .id(longCount.incrementAndGet())
            .message(UUID.randomUUID().toString())
            .senderRefTable(UUID.randomUUID().toString())
            .senderRefId(longCount.incrementAndGet())
            .receiverRefTable(UUID.randomUUID().toString())
            .receiverRefId(longCount.incrementAndGet())
            .status(UUID.randomUUID().toString())
            .studentId(longCount.incrementAndGet())
            .schoolId(longCount.incrementAndGet())
            .lastModifiedBy(UUID.randomUUID().toString());
    }
}
