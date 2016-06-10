<?php
//Checa que o formulário foi enviado
if( !empty( $_POST ) ){
         //Cria a array com os dados recebido
      $postArray = array(
        "codigo" => $_POST['codigo'],
        "marca" => $_POST['marca'],
        "modelo" => $_POST['modelo'],
        "ano" => $_POST['ano'],
        "potencia" => $_POST['potencia'],
        "carga" => $_POST['carga'],
        "complemento" => $_POST['complemento'],
    ); 
// Converte os dados para o formato jSon
$json = json_encode( $postArray );
};
  //Inicia a biblioteca cURL do PHP
  $curl = curl_init();
  curl_setopt_array($curl, array(
  CURLOPT_PORT => "56179", //porta do WS
  CURLOPT_URL => "http://localhost:56179/trabalhosd/webresources/Carro/Carro/atualizar/", //Caminho do WS que vai receber o GET
  CURLOPT_RETURNTRANSFER => true, //Recebe resposta
  CURLOPT_ENCODING => "JSON", //Decodificação
  CURLOPT_MAXREDIRS => 10,
  CURLOPT_TIMEOUT => 30,
  CURLOPT_HTTP_VERSION => CURL_HTTP_VERSION_1_1,
  CURLOPT_CUSTOMREQUEST => "PUT", //metodo
  CURLOPT_POSTFIELDS => $json, //string com dados à serem postados      
  CURLOPT_HTTPHEADER => array(                                                                          
    'Content-Type: application/json',                                                                                
    'Content-Length: ' . strlen($json)),
  ));                                                                                                                   
$result = curl_exec($curl); //recebe o resultado
$err = curl_error($curl); //recebe o erro da classe ou WS

curl_close($curl); //Encerra a biblioteca

if ($err) {
?>

  <script type="text/javascript"> //apresenta o erro
    alert("<?php echo $err; ?>"); 
    window.location.href = "http://localhost:9090/carrosd/index.php"; 
  </script >

<?php
} else {    //valadia a condição, tendo erro, alerta o erro, ou segue para o resultado vindo do WS
?>
 
<script type="text/javascript"> //apresenta o resultado
    alert("<?php echo $result; ?>"); 
    window.location.href = "http://localhost:9090/carrosd/carro.php?action=detail&id="+<?php echo $_POST['codigo'];?>; 
  </script>
<?php
}//encerra else
?>