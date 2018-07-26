package istio.java.gen;

import java.io.File;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.snowdrop.istio.api.model.IstioResource;
import me.snowdrop.istio.api.model.IstioResourceBuilder;
import me.snowdrop.istio.api.model.v1.networking.PortSelector;
import org.yaml.snakeyaml.Yaml;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;


public class IstioGenClient {

    public static void main(String[] args) throws Exception {

        testIstioIngressGen();
        testIstioVirtualServiceGen();

    }

    static void testIstioIngressGen() throws Exception {
        Map<String, String> selectorMap = new HashMap();
        selectorMap.put("istio", "ingressgateway");
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
                        .build())
                .endGatewaySpec()
                .build();

        YAMLMapper mapper = new YAMLMapper();
        mapper.writeValue(new File("./ballerina_istio_gw.yaml"), gateway);
    }

    static void testIstioVirtualServiceGen() throws Exception {

        final IstioResource virtualService = new IstioResourceBuilder()
                .withApiVersion("networking.istio.io/v1alpha3")
                .withNewMetadata()
                .withName("ballerina-time-service")
                .endMetadata()
                .withNewVirtualServiceSpec()
                .withHosts("*")
                .addNewHttp()
                .addNewMatch().withNewUri("/dfdf").endMatch()
                .addNewRoute()
                .withNewDestination()
                .withHost("ballerina-time-service")
                .withPort(new PortSelector("9095"))
                .endDestination()
                .endRoute()
                .endHttp()
                .endVirtualServiceSpec()
                .build();

        YAMLMapper mapper = new YAMLMapper();
        mapper.writeValue(new File("./istio-java-virtualservice.yaml"), virtualService);


    }


}