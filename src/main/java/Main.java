import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
    private String name;

    public static void main(String[] args) {
        Main m = new Main();
        try {
            m.process();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void process() throws Exception {
        PostgresConn conn = new PostgresConn();
        Connection connection = conn.getConnection();
        Statement s = connection.createStatement();
        Statement s1 = connection.createStatement();
        String q = "Select * from store";
        ResultSet rs = s.executeQuery(q);
        String path = "c:\\".concat("my-share\\").concat(new FileName().getPath());
        Files.createDirectories(Paths.get(path));
        while (rs.next()) {
            String code = rs.getString(1);
            String name = rs.getString(2);
            String fileName = new FileName().createFileName(code, name);
            System.out.println(code + " <> " + name + " <> "+ path+"\\"+ fileName);
            String q1 = "select q.store_code as \"WAREHOUSE_ID\",s.name as \"WAREHOUSE_NAME\",q.item_code as \"PRODUCT_CODE\",CAST(q.quantity AS INTEGER) as \"PHYSICAL QTY\"\n" +
                    " from store_quantity_base q join item i on i.code=q.item_code join store s on s.code=q.store_code where q.store_code='" + code + "'";
            ResultSet r = s1.executeQuery(q1);

            try (PrintWriter writer = new PrintWriter(path.concat(fileName).concat(".csv"), "UTF-8")) {
                while (r.next()) {
                    String line = (r.getString(1) + "," + r.getString(2) + "," + r.getString(3) + "," + r.getString(4));
                    writer.println(line);
                    //writer.println("second line!");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            //TODO implement camel for fetching stock updata data, creating the file and sharing via ftp
            //StockUpdateRoute route = new StockUpdateRoute(code, name);
            //route.configure();
        }
    }
}
