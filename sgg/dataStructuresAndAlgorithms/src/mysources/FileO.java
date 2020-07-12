package mysources;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class FileO {
    public static void copy(String src,String dis) throws Exception{
        FileInputStream fileInputStream = new FileInputStream(new File(src));

        File disFile = new File(dis);
        FileOutputStream fileOutputStream = new FileOutputStream(disFile,true);

        byte[] b = new byte[1024];

        //从配置中读取
        long count = 0;

        if(disFile.exists()){
            count = disFile.length();
        }

        int res = -1;
        fileInputStream.skip(count);

        while((res = fileInputStream.read(b))!=-1){
            fileOutputStream.write(b,0,res);
            fileOutputStream.flush();
        }
        System.out.println("复制完成");
    }

    public static void main(String[] args) throws Exception{
        copy("F:/a.doc","F:/b.doc");
    }
}
