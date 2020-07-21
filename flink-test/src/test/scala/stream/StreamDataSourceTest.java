package stream;

import static org.junit.jupiter.api.Assertions.*;

class StreamDataSourceTest {

    @org.junit.jupiter.api.Test
    void readFromMySource() {
        StreamDataSource.readFromMySource().print();
    }
}