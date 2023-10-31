package route;

import org.apache.camel.builder.RouteBuilder;

public class StockUpdateRoute extends RouteBuilder {
    private String storeCode;
    private String storeName;
    public StockUpdateRoute(String code, String name){
        this.storeCode = code;
        this.storeName = name;
    }
    @Override
    public void configure() throws Exception {
        from("direct:select")
                .setBody(constant("select q.store_code as \"WAREHOUSE_ID\",s.name as \"WAREHOUSE_NAME\"," +
                        "q.item_code as \"PRODUCT_CODE\",CAST(q.quantity AS INTEGER) as \"PHYSICAL QTY\" " +
                        "from store_quantity_base q join item i on i.code=q.item_code join store s on s.code=q.store_code " +
                        "where q.store_code='"+storeCode+"'"))
                .to("jdbc:datasource")
                .log("store "+storeCode+" query ran!!!!!")
                .wireTap("direct:file");
        from("direct:file")
                .marshal().csv()
                .to("file:c:\\my-share?fileName=MY_TEST_FILE.csv")
                .log("store "+storeCode+" file processed!!!!");
    }
}
