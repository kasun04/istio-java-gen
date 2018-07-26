package istio.java.gen;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.snowdrop.istio.api.model.IstioResource;
import me.snowdrop.istio.api.model.IstioResourceBuilder;
import org.yaml.snakeyaml.Yaml;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;




public class IstioGenClient {
    /*
    ---
apiVersion: networking.istio.io/v1alpha3
kind: Gateway
metadata:
  name: httpbin-gateway
spec:
  selector:
    istio: ingressgateway # use Istio default gateway implementation
  servers:
  - port:
      number: 80
      name: http
      protocol: HTTP
    hosts:
    - "httpbin.example.com"
     */

    public static void main(String[] args) throws Exception {


        Map<String, String> selectorMap = new HashMap();
        selectorMap.put("istio","ingressgateway");
        final IstioResource gateway = new IstioResourceBuilder()
                .withApiVersion("networking.istio.io/v1alpha3")
                .withNewMetadata()
                .withName("ballerina-time-service-gateway")
                .endMetadata()
                .withNewGatewaySpec()
                .withSelector(selectorMap)
                .withServers(new me.snowdrop.istio.api.model.v1.networking.ServerBuilder()
                        .withNewPort("http", 80, "HTTP")
                        .withHosts("*")
                        .build() )
                .endGatewaySpec()
                .build();

        YAMLMapper mapper = new YAMLMapper();
        mapper.writeValue(new File("./ballerina_istio_gw.yaml"), gateway);


    }


}