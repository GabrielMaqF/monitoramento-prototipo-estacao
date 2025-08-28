//package com.maqfiltros.sensors_contract.mqtt;
//
//import java.time.Instant;
//
//import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.integration.annotation.ServiceActivator;
//import org.springframework.integration.channel.DirectChannel;
//import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
//import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
//import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
//import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
//import org.springframework.integration.support.MessageBuilder;
//import org.springframework.messaging.MessageChannel;
//import org.springframework.messaging.MessageHandler;
//
//import com.maqfiltros.sensors_contract.config.MqttProperties;
//import com.maqfiltros.sensors_contract.entities.Equipamento;
//import com.maqfiltros.sensors_contract.entities.Leitura;
//import com.maqfiltros.sensors_contract.entities.SensorNivel;
//import com.maqfiltros.sensors_contract.services.EquipamentoService;
//import com.maqfiltros.sensors_contract.services.LeituraService;
//import com.maqfiltros.sensors_contract.sse.SseService;
//
////@Configuration
//
//public class MqttConfig {
//
//	@Autowired
//	private MqttProperties mqttProperties;
//
//	@Bean
//	MqttPahoClientFactory mqttClientFactory() {
//		DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
//
//		MqttConnectOptions options = new MqttConnectOptions();
//		options.setServerURIs(new String[] { mqttProperties.getUrl() });
//		options.setCleanSession(false);
//		options.setAutomaticReconnect(true); // Habilita a reconexão automática
//		options.setConnectionTimeout(10); // Tempo limite para tentativa de conexão (em segundos)
//		options.setKeepAliveInterval(30); // Intervalo de keep-alive (em segundos)
//		options.setMaxReconnectDelay(60000); // Máximo de 1 minuto entre tentativas
//
//		factory.setConnectionOptions(options);
//		return factory;
//	}
//
//	@Bean
//	MessageChannel mqttInputChannel() {
//		return new DirectChannel();
//	}
//
//	@Bean
//	MqttPahoMessageDrivenChannelAdapter inbound(MqttPahoClientFactory mqttClientFactory) {
//		MqttPahoMessageDrivenChannelAdapter adapter = new MqttPahoMessageDrivenChannelAdapter("mqttClient",
//				mqttClientFactory, "leitura/#");
//		adapter.setQos(2); // QoS 2: Exactly Once
//		adapter.setOutputChannel(mqttInputChannel());
//
//		return adapter;
//	}
//
//	@Bean
//	MessageChannel mqttOutputChannel() {
//		return new DirectChannel();
//	}
//
//	@Bean
//	@ServiceActivator(inputChannel = "mqttOutputChannel")
//	MessageHandler mqttOutbound() {
//		MqttPahoMessageHandler handler = new MqttPahoMessageHandler("mqttClientPublisher", mqttClientFactory());
//		handler.setAsync(false); // Alterado para síncrono
//		return handler;
//	}
//
//	@Bean
//	@ServiceActivator(inputChannel = "mqttInputChannel")
//	MessageHandler mqttMessageHandler(LeituraService leituraService, EquipamentoService equipamentoService,
//			MessageChannel mqttOutputChannel, SseService sseService) {
//		return message -> {
//			try {
//				String topic = message.getHeaders().get("mqtt_receivedTopic", String.class);
//
//				// Verifique se o payload já é uma String
//				String payload = null;
//				if (message.getPayload() instanceof String) {
//					payload = (String) message.getPayload(); // Caso já seja uma String
//				} else if (message.getPayload() instanceof byte[]) {
//					payload = new String((byte[]) message.getPayload()); // Caso seja um array de bytes
//				}
//
//				// Ignorar tópicos de confirmação
//				if (topic != null && topic.endsWith("/confirmacao")) {
//					System.out.println("Mensagem de confirmação recebida. Ignorando.");
//					return; // Não processar mensagens de confirmação
//				}
//
//				if (topic != null && topic.startsWith("leitura/")) {
//					// Extrair informações do tópico
//					String[] parts = topic.split("/");
//					if (parts.length == 2) {
//						Long equipamentoId = Long.parseLong(parts[1]); // ID do Equipamento
//						String valor = payload; // Valor enviado no payload da mensagem
//
//						Equipamento equipamento = equipamentoService.findById(equipamentoId);
////                        EquipamentoServiceGeneric<?, ?> equipamentoService = serviceFactory.getService(TipoEquipamento.Hidrometro); // Usa um como padrão para o findById
////                        Equipamento equipamento = equipamentoService.findById(equipamentoId);
//						// Processar leitura
////						Equipamento equipamento = equipamentoService.findById(equipamentoId);
//						switch (equipamento.getTipoEquipamento()) {
//						case Hidrometro: {
//
//							break;
//						}
//						case SensorNivel: {
//							SensorNivel sn = (SensorNivel) equipamento;
//							valor = "" + (sn.getAlturaTotalReservatorioCm() - (Double.parseDouble(valor)
//									- (sn.getAlturaEquipamentoCm() - sn.getAlturaTotalReservatorioCm())));
//
//							break;
//						}
//						default:
//							throw new IllegalArgumentException("Unexpected value: " + equipamento.getTipoEquipamento());
//						}
//
//						Leitura leitura = new Leitura(null, Instant.now(), valor, equipamento);
//
//						leituraService.insert(leitura);
//
//						// Enviar confirmação síncrona
//						String confirmacao = "Recebido e processado: " + valor;
//						mqttOutputChannel.send(
//								MessageBuilder.withPayload(confirmacao).setHeader("mqtt_topic", topic + "/confirmacao") // Responde
//																														// no
//																														// mesmo
//																														// tópico
//																														// com
//																														// o
//																														// sufixo
//																														// "/confirmacao"
//										.setHeader("mqtt_qos", 2) // QoS 2
//										.build());
//						String messageSSE = ("{ \"id_cliente\": \"" + equipamento.getEscola().getId()
//								+ "\", \"id_equipamento\": \"" + equipamento.getId() + "\", \"id_leitura\": \""
//								+ leitura.getId() + "\", \"x\": \"" + leitura.getMoment() + "\", \"y\": \""
//								+ leitura.getValor() + "\" }");
//						sseService.sendEvent(messageSSE);
//
//						System.out.println("SSE: " + messageSSE);
//
//						System.out.println("Mensagem confirmada: " + confirmacao);
//					} else {
//						System.err.println("Tópico inválido: " + topic);
//					}
//				} else {
//					System.err.println("Mensagem recebida de tópico inesperado: " + topic);
//				}
//			} catch (Exception e) {
//				e.printStackTrace();
//				System.err.println("Erro ao processar mensagem MQTT: " + e.getMessage());
//			}
//		};
//	}
//}