<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
  "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
    <head>
        <title>Consumindo WebService</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <link href="css/estilo.css" rel="stylesheet" type="text/css"/>
        <script> //Função do botão

            //Função do botão editar
            function edit(id){
                window.location = "http://localhost:9090/carrosd/carro.php?action=put&id="+id;
            };

            //Função do botão cadastrar
            function abrir(){
                window.location = "http://localhost:9090/carrosd/carro.php"
            };

            //Função do botão listar
            function listar(){
                window.location = "http://localhost:9090/carrosd/index.php"
            };

            //Função do botão excluir
            function excluir(id){
            if (confirm("Deseja excluir?"))
                    window.location = "http://localhost:9090/carrosd/index.php?action=del_id&id="+id;
            }; 
        </script>
    </head>
    
    <body>
    
        <h1>CArros</h1>
    
        <?php
            //trata a URL, se possui GET action e id, executa exclusao, caso contrario trata o GET da lista linha 87
            if (isset($_GET["action"]) && isset($_GET["id"]) && $_GET["action"] == "detail") 
            {
            //Recupera o ID do GET da URL    
            $id = $_GET["id"];
            //Inicia a biblioteca cURL do PHP
              //Inicia a biblioteca cURL do PHP
              $curl = curl_init();
              curl_setopt_array($curl, array(
              CURLOPT_PORT => "56179", //porta do WS
              CURLOPT_URL => "http://localhost:56179/trabalhosd/webresources/Carro/Carro/get/". $id, //Caminho do WS que vai receber o GET
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
            $c = json_decode($data1); //Decodifica o retorno gerado no modelo jSon

                //Define as arrays
                 $id = $c->codigo;
                 $marca = $c->marca;
                 $modelo = $c->modelo;
                 $ano = $c->ano;
                 $potencia = $c->potencia;
                 $carga = $c->carga;
                 $complemento = $c->complemento;

        ?>

        <form name="postform" id="postform" class="rounded" enctype="multipart/form-data">

            <h2>Formulário GET</h2>

            <div class="field">
                <label for="codigo">Código:</label>
                <input type="number" class="input" id="codigo" name="codigo" value="<?php echo $id;?>" readonly/>
                <p class="hint">Código (Automático)!</p>
            </div>  

            <div class="field">
                <label for="marca">Marca:</label>
                <input type="text" class="input" id="marca" name="marca" value="<?php echo $marca;?>" readonly/>
                <p class="hint">Digite a Marca.</p>
            </div>

            <div class="field">
                <label for="modelo">Modelo:</label>
                <input type="text" class="input" id="modelo" name="modelo" value="<?php echo $modelo;?>" readonly/>
                <p class="hint">Digite o Modelo.</p>
            </div>  

            <div class="field">
                <label for="ano">Ano:</label>
                <input type="number" class="input" id="ano" name="ano" value="<?php echo $ano;?>" readonly/>
                <p class="hint">Digite o Ano.</p>
            </div>

            <div class="field">
                <label for="potencia">Potência:</label>
                <input type="number" class="input" id="potencia" name="potencia" value="<?php echo $potencia;?>" readonly/>
                <p class="hint">Digite a Potência.</p>
            </div>  

            <div class="field">
                <label for="carga">Carga:</label>
                <input type="number" class="input" id="carga" name="carga" value="<?php echo $carga;?>" readonly/>
                <p class="hint">Digite a Capacidade de Carga.</p>
            </div>

            <div class="field">
                <label for="modelo">Complemento:</label>
                <input type="text" class="input" id="complemento" name="complemento" value="<?php echo $complemento;?>" readonly/>
                <p class="hint">Digite o Complemento.</p>
            </div>  


                <button type="button" onclick="listar()" class="botao">Listar</button>
                <button type="button" onclick="edit(<?PHP echo $id;?>)" class="botao">Editar</button>
                <button type="button" onclick="excluir(<?PHP echo $id;?>)" class="botao">Excluir</button>
                <button type="button" onclick="abrir()" class="botao">Novo</button>
        </form>
        
        <?php } 

            else if (isset($_GET["action"]) && isset($_GET["id"]) && $_GET["action"] == "put") {
                //Recupera o ID do GET da URL    
                $id = $_GET["id"];

                //Inicia a biblioteca cURL do PHP
                    //Inicia a biblioteca cURL do PHP
                    $curl = curl_init();
                    curl_setopt_array($curl, array(
                    CURLOPT_PORT => "56179", //porta do WS
                    CURLOPT_URL => "http://localhost:56179/trabalhosd/webresources/Carro/Carro/get/". $id, //Caminho do WS que vai receber o GET
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
                  $c = json_decode($data1); //Decodifica o retorno gerado no modelo jSon

                      //Define as arrays
                       $id = $c->codigo;
                       $marca = $c->marca;
                       $modelo = $c->modelo;
                       $ano = $c->ano;
                       $potencia = $c->potencia;
                       $carga = $c->carga;
                       $complemento = $c->complemento;

        ?>

        <form name="postform" id="postform" class="rounded" action="put.php" method="post" enctype="multipart/form-data">

            <h2>Formulário GET/PUT</h2>

            <div class="field">
                <label for="codigo">Código:</label>
                <input type="number" class="input" id="codigo" name="codigo" value="<?php echo $id;?>" readonly/>
                <p class="hint">Código (Automático)!</p>
            </div>  

            <div class="field">
                <label for="marca">Marca:</label>
                <input type="text" class="input" id="marca" name="marca" value="<?php echo $marca;?>"/>
                <p class="hint">Digite a Marca.</p>
            </div>

            <div class="field">
                <label for="modelo">Modelo:</label>
                <input type="text" class="input" id="modelo" name="modelo" value="<?php echo $modelo;?>"/>
                <p class="hint">Digite o Modelo.</p>
            </div>  

            <div class="field">
                <label for="ano">Ano:</label>
                <input type="number" class="input" id="ano" name="ano" value="<?php echo $ano;?>"/>
                <p class="hint">Digite o Ano.</p>
            </div>

            <div class="field">
                <label for="potencia">Potência:</label>
                <input type="number" class="input" id="potencia" name="potencia" value="<?php echo $potencia;?>" />
                <p class="hint">Digite a Potência.</p>
            </div>  

            <div class="field">
                <label for="carga">Carga:</label>
                <input type="number" class="input" id="carga" name="carga" value="<?php echo $carga;?>"/>
                <p class="hint">Digite a Capacidade de Carga.</p>
            </div>

            <div class="field">
                <label for="modelo">Complemento:</label>
                <input type="text" class="input" id="complemento" name="complemento" value="<?php echo $complemento;?>"/>
                <p class="hint">Digite o Complemento.</p>
            </div>  


                <input type="submit" value="Alterar" class="botao"/>
                <input type="reset" value="Limpar" class="botao">
                    <button type="button" onclick="listar()"class="botao">Listar</button>
                <button type="button" onclick="excluir(<?PHP echo $id;?>)" class="botao">Excluir</button>
                <button type="button" onclick="abrir()" class="botao">Novo</button>
            </form>
        
            <?php 
             
                }else {
            ?>     
            
            <form name="postform" id="postform" class="rounded" action="post.php" method="post" 
                  enctype="multipart/form-data">

                <h2>Formulário POST</h2>

                <div class="field">
                    <label for="codigo">Código:</label>
                    <input type="number" class="input" id="codigo" name="codigo" disabled/>
                    <p class="hint">Código (Automático)!</p>
                </div>  

                <div class="field">
                    <label for="marca">Marca:</label>
                    <input type="text" class="input" id="marca" name="marca" />
                    <p class="hint">Digite a Marca.</p>
                </div>

                <div class="field">
                    <label for="modelo">Modelo:</label>
                    <input type="text" class="input" id="modelo" name="modelo"/>
                    <p class="hint">Digite o Modelo.</p>
                </div>  

                <div class="field">
                    <label for="ano">Ano:</label>
                    <input type="number" class="input" id="ano" name="ano" />
                    <p class="hint">Digite o Ano.</p>
                </div>

                <div class="field">
                    <label for="potencia">Potência:</label>
                    <input type="number" class="input" id="potencia" name="potencia" />
                    <p class="hint">Digite a Potência.</p>
                </div>  

                <div class="field">
                    <label for="carga">Carga:</label>
                    <input type="number" class="input" id="carga" name="carga" />
                    <p class="hint">Digite a Capacidade de Carga.</p>
                </div>

                <div class="field">
                    <label for="modelo">Complemento:</label>
                    <input type="text" class="input" id="complemento" name="complemento"/>
                    <p class="hint">Digite o Complemento.</p>
                </div>  

                <input type="submit" value="Enviar" class="botao"/>
                <input type="reset" value="Limpar" class="botao">
                <button type="button" onclick="listar()" class="botao">Listar</button>

             <?php 
                } 
             ?>
            
    </body>
    
</html>