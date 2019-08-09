import javax.sound.midi.SysexMessage;
import java.io.*;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.*;

public class Greetings {
    // 执行间隔
     private static final long PERIOD_DAY = 24 * 60 * 60 * 1000;
    public static void main(String[] args) {
        final String filePath = "greeting-list.txt";

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 1); //凌晨1点
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date ddate=calendar.getTime(); //第一次执行定时任务的时间
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                try {
                    BufferedReader bf = new BufferedReader(new FileReader(new File(
                            filePath)));
                    String line;
                    while ((line = bf.readLine())!=null) {
                        if (line != null) {
                            String lineStr = line.toString();
                            String arr[] = lineStr.split(",");
                            Date user_birth = strToDate(arr[2]);
                            if(null!=user_birth) {
                                Calendar casys = Calendar.getInstance();
                                Calendar casuser = Calendar.getInstance();
                                casuser.setTime(user_birth);
                                casys.setTime(new Date());
                                int mon_user = casuser.get(Calendar.MONTH);
                                int day_user = casuser.get(Calendar.DATE);
                                int mon_sys = casys.get(Calendar.MONTH);
                                int day_sys = casys.get(Calendar.DATE);
                                if (day_user == day_sys && mon_user==mon_sys) {
                                    System.out.println("Subject:Happy birthday!");
                                    System.out.println("Happy birthday,dear " + arr[0]);
                                }
                            } else {
                                System.out.println("tips:"+arr[0]+" birthday format eror！" );

                            }

                        }

                    }
                    bf.close();
                    System.out.println("finish!" );
                    //再把listNum中的数据依次放入表格中
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        };
        timer.schedule(task,ddate,PERIOD_DAY);
    }

    public static Date strToDate(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        formatter.setLenient(false);
         ParsePosition pos = new ParsePosition(0);
         Date daters = formatter.parse(strDate, pos);
        return daters;
        }


}
