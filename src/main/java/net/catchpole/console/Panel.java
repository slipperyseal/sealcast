package net.catchpole.console;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Panel {
    public static void main(String args[]) throws Exception {
        ConsolePanel consolePanel = new ConsolePanel(System.out);

        consolePanel.addHeading(1).update("Test Thing");
        DataUpdate<String> info = consolePanel.addRow(2);

        consolePanel.addHeading(4).update("Title");
        DataUpdate<List<String>> listDataUpdate = consolePanel.addList(5, 10);

        int x=0;
        for (;;) {
            info.update(" x = " + (x++) + "\t" + (15-x));

            List<String> list = new ArrayList<String>();
            for (int y=0;y<(x & 0xf);y++) {
                list.add("Item " + y + " " + new Date());
            }

            listDataUpdate.update(list);

            Thread.sleep(1000);
        }
    }
}
