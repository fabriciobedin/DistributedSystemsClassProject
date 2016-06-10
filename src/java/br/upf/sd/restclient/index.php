<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
  "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
    
    <head>
        
        <title>Consumindo WebService</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <link href="css/estilo.css" rel="stylesheet" type="text/css"/> 
        <script src="js/jquery.js" type="text/javascript"></script>
        <script src="js/jquery.tablesorter.js" type="text/javascript"></script>
        <script type="text/javascript">
            //Chama a função tablesorter, plugin do jQuery faz a classificação das colunas
            $(function() {		
		$("#carro").tablesorter({sortList: [[1,0],[2,0]]}); //sortList Classifica as colinas 1 e 2 em ordem crescente
            });	
            
            //chama função ready do jQuery
            $(document).ready(function() {
                //chama a sub-função click, que vai tratar a linha da tabela
                $("#carro td").click(function(){
                    //$this mantem o foco no objeto, onde vai procurar a referencia de link a, e attribuir a sua função href
                    var href = $(this).find("a").attr("href");
                    if(href) {
                        //abre o link da linha correspondente
                        window.location = href; 
                    }
                });
            });
            
        //Função do botão excluir mais Alerta de excluzão  
        function excluir(id){
            if (confirm("Deseja excluir?"))
                    window.location = "http://localhost:9090/carrosd/index.php?action=del_id&id="+id;
            };
        
        //Função do botão editar
        function edit(id){
            window.location = "http://localhost:9090/carrosd/carro.php?action=put&id="+id;
        };
        
        //Função do botão editar
        function detail(id){
            window.location = "http://localhost:9090/carrosd/carro.php?action=detail&id="+id;
        };
        
        //Função do botão cadastrar
        function abrir(){
            window.location = "http://localhost:9090/carrosd/carro.php"
        };
            
      	</script>
        
    </head>
    
    <body align="center">

        <?php
            //trata a URL, se possui GET action e id, executa exclusao, caso contrario trata o GET da lista linha 87
            if (isset($_GET["action"]) && isset($_GET["id"]) && $_GET["action"] == "del_id") {
                //Recupera o ID do GET da URL    
                $id = $_GET["id"];
                //Inicia a biblioteca cURL do PHP
                $curl = curl_init();

                curl_setopt_array($curl, array(
                  CURLOPT_PORT => "56179", //porta do WS
                  CURLOPT_URL => "http://localhost:56179/trabalhosd/webresources/Carro/Carro/excluir/". $id, //Caminho do WS + string do DELETE, recuperado pelo GET
                  CURLOPT_RETURNTRANSFER => true, //Recebe resposta
                  CURLOPT_ENCODING => "", //Decodificação
                  CURLOPT_MAXREDIRS => 10,
                  CURLOPT_TIMEOUT => 30,
                  CURLOPT_HTTP_VERSION => CURL_HTTP_VERSION_1_1,
                  CURLOPT_CUSTOMREQUEST => "DELETE", //metodo
                  CURLOPT_HTTPHEADER => array(
                    "cache-control: no-cache",
                   ),
                ));

                $response = curl_exec($curl); //recebe a resposta do WS
                $err = curl_error($curl); //recebe o erro da classe ou WS

                curl_close($curl); //encerra classe

            if ($err) {
        ?>
        <script type="text/javascript"> //apresenta o erro
            alert("<?php echo $err; ?>"); 
            window.location.href = "http://localhost:9090/carrosd/index.php"; 
        </script>
        <?php
            } else {    //caso contratio
        ?>

            <h1>Carro</h1>

        <?php
            }
        ?>

        <script type="text/javascript"> //apresenta a resposta
            alert("<?php echo $response; ?>"); 
            window.location.href = "http://localhost:9090/carrosd/index.php"; 
        </script> 

        <?php
            }  
            else
            {     //Caso não tenha os GETs na URL da pagina, trata o GET da lista do WS
        ?> 

        <h1>Carros</h1>
        
        <table id="carro" align="center" class="table">

            <caption><h2>Lista GET</h2></caption> 

        <thead>
            <tr>
                <th>Código</th>
                <th>Marca</th>
                <th>Modelo</th>
                <th>Ano</th>
                <?php 
                /*
                *<th>Potência</th>
                *<th>Carga</th>
                *<th>Complemento</th>
                */?>
                <th></th>
            </tr>
        </thead>

        <tbody>
            <?php
              //Inicia a biblioteca cURL do PHP
              $curl = curl_init();
              curl_setopt_array($curl, array(
              CURLOPT_PORT => "56179", //porta do WS
              CURLOPT_URL => "http://localhost:56179/trabalhosd/webresources/Carro/Carro/list", //Caminho do WS que vai receber o GET
              CURLOPT_RETURNTRANSFER => true, //Recebe resposta
              CURLOPT_ENCODING => "JSON", //Decodificação
              CURLOPT_MAXREDIRS => 10,
              CURLOPT_TIMEOUT => 30,
              CURLOPT_HTTP_VERSION => CURL_HTTP_VERSION_1_1,
              CURLOPT_CUSTOMREQUEST => "GET", //metodo
              CURLOPT_HTTPHEADER => array(
                "cache-control: no-cache",
               ),
            )); //recebe retorno
            $data1 = curl_exec($curl); //Recebe a lista no formato jSon do WS
            curl_close($curl); //Encerra a biblioteca
            $data = json_decode($data1); //Decodifica o retorno gerado no modelo jSon
            //$clientes = $data->cliente; função de selecionar o obejto nao suportada pelo POST do WS
                foreach ($data as $c) //cria a classe de tratamento
                {
                //Define as arrays
                 $id = $c->codigo;
                 $marca = $c->marca;
                 $modelo = $c->modelo;
                 $ano = $c->ano;
                 $potencia = $c->potencia;
                 $carga = $c->carga;
                 $complemento = $c->complemento;
            ?>

                <tr id="<?php echo $id; /*pubica as informações na tabela>*/?>" class="trClick" rel="carro">   
                    <td align="right" width="60px"><a class="fancybox" href="carro.php?action=detail&id=<?php echo $id; ?>">
                        <?php echo $id; ?></a></td>
                    <td width="100px"><a class="fancybox" href="carro.php?action=detail&id=<?php echo $id; ?>">
                        <?php echo $marca; ?></a></td>
                    <td width="100px"><a class="fancybox" href="carro.php?action=detail&id=<?php echo $id; ?>">
                        <?php echo $modelo; ?></a></td>
                    <td width="80px"><a class="fancybox" href="carro.php?action=detail&id=<?php echo $id; ?>">
                        <?php echo $ano; ?></a></td>
                    <?php 
                    /*
                    *<td width="80px"><?php echo $potencia; ?></td>
                    *<td width="80px"><?php echo $carga; ?></td>
                    *<td width="80px"><?php echo $complemento; ?></td>
                     */
                    ?>
                    <td align="center" width="90px"> 
                        <?php echo "<a href='#' onclick=detail(\"$id\")";/*chama o script da exclusão, para o id da linha*/?> 
                        title= "Listar <?php echo $modelo; ?>"><img width="12px" height="15px"src="css/file.gif" id="D"></a> &nbsp;&nbsp;&nbsp;        
                        <?php echo "<a href='#' onclick=edit(\"$id\")";/*chama o script da exclusão, para o id da linha*/?> 
                        title= "Editar <?php echo $modelo; ?>"><img width="15px" height="15px"src="css/lapis.gif" id="E"></a> &nbsp;&nbsp;&nbsp;    <?php echo "<a href='#' onclick=excluir(\"$id\")";/*chama o script da exclusão, para o id da linha*/?> 
                        title= "Excluir <?php echo $modelo; ?>"><img width="15px" height="15px"src="css/lixeira.gif" id="X">
                         </a>
                    </td>
                </tr>

            <?php
                }//encerra PHP do tratamento da lista
            ?>

        </tbody>

            <?php
                }//encerra PHP else
            ?>     

            <tr style="background-color: f1f1f1; cursor: default; hover: #ccc;">
                <td colspan="5" align="center">
                    <button type="button" onclick="abrir()" class="botao" style="width: 110px; float: none;" >Cadastrar</button>
                </td>
            </tr>

        </table>

    </body>

</html>