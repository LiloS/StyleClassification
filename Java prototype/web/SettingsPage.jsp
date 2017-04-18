<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="content-type" content="text/html" charset="UTF-8">
    <script class="jsbin" src="http://ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js"></script>
    <script class="jsbin" src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8.0/jquery-ui.min.js"></script>
    <title>Classification</title>
    <link rel="stylesheet" type="text/css" href="resources/templatemo_476_conquer/css/style.css"/>
    <style>
        .myButton {
            -moz-box-shadow: inset 0px 1px 3px 0px #91b8b3;
            -webkit-box-shadow: inset 0px 1px 3px 0px #91b8b3;
            box-shadow: inset 0px 1px 3px 0px #91b8b3;
            background: -webkit-gradient(linear, left top, left bottom, color-stop(0.05, #b4b8b7), color-stop(1, #6c7c7c));
            background: -moz-linear-gradient(top, #b4b8b7 5%, #6c7c7c 100%);
            background: -webkit-linear-gradient(top, #b4b8b7 5%, #6c7c7c 100%);
            background: -o-linear-gradient(top, #b4b8b7 5%, #6c7c7c 100%);
            background: -ms-linear-gradient(top, #b4b8b7 5%, #6c7c7c 100%);
            background: linear-gradient(to bottom, #b4b8b7 5%, #6c7c7c 100%);
            filter: progid:DXImageTransform.Microsoft.gradient(startColorstr='#b4b8b7', endColorstr='#6c7c7c', GradientType=0);
            background-color: #b4b8b7;
            -moz-border-radius: 15px;
            -webkit-border-radius: 15px;
            border-radius: 15px;
            display: inline-block;
            cursor: pointer;
            color: #ffffff;
            font-family: Arial;
            font-size: 15px;
            font-weight: bold;
            padding: 11px 76px;
            text-decoration: none;
            text-shadow: 1px 2px 5px #2b665e;
        }

        .myButton:hover {
            background: -webkit-gradient(linear, left top, left bottom, color-stop(0.05, #6c7c7c), color-stop(1, #b4b8b7));
            background: -moz-linear-gradient(top, #6c7c7c 5%, #b4b8b7 100%);
            background: -webkit-linear-gradient(top, #6c7c7c 5%, #b4b8b7 100%);
            background: -o-linear-gradient(top, #6c7c7c 5%, #b4b8b7 100%);
            background: -ms-linear-gradient(top, #6c7c7c 5%, #b4b8b7 100%);
            background: linear-gradient(to bottom, #6c7c7c 5%, #b4b8b7 100%);
            filter: progid:DXImageTransform.Microsoft.gradient(startColorstr='#6c7c7c', endColorstr='#b4b8b7', GradientType=0);
            background-color: #6c7c7c;
        }

        .myButton:active {
            position: relative;
            top: 1px;
        }

        progress {
            background-color: #d1ddd1;
            border: 0;
            height: 18px;
            border-radius: 9px;
        }

        ul {
            list-style-type: none;
            margin: 0;
            padding: 0;
            overflow: hidden;
            border: 1px solid #e7e7e7;
            background-color: #f3f3f3;
        }

        li {
            float: left;
        }

        li a {
            display: block;
            color: #666;
            text-align: center;
            padding: 14px 16px;
            text-decoration: none;
        }

        li a:hover:not(.active) {
            background-color: #ddd;
        }

        li a.active {
            color: white;
            background-color: #585e59;
        }
    </style>
</head>
<body>
<div align="center">
    <div style="background-image: url(resources/images/bg1.jpg); height: 200px; text-align: center;" ;>
        <p align="right">Login as </p>
        <p align="right"><c:out value="${usernameDisplay}" default="guest"/></p>
        <h1>Settings</h1></div>
    <ul>
        <li><a class="active" href="HomePage">Home</a></li>
        <li><a id="history_link_to_change" href="#" onclick="ChangeHistoryLink()">History</a></li>
        <li><a id="settings_Link" href="SettingsPage">Settings</a></li>
    </ul>
    <form method="post" action="SettingsPageServlet" enctype="multipart/form-data">
        <div align="center">
            <h2 style="text-align: center;">Image selection</h2>

            <p><img id="blah" src="#" alt="Please select image for classification"
                    style="width: 50%; height: 50%; border-width: 2px; border-style: solid; margin: 2px;"/></p>

            <p>Select image for classification: </p>
            <input type="file" name="file" size="60" onchange="readURL(this);"/>
            <br/>
            <p>Style:</p>
            <select name="style">
                <option value="Abstract Art">Abstract Art</option>
                <option value="Abstract Expressionism">Abstract Expressionism</option>
                <option value="Academicism">Academicism</option>
                <option value="Action painting">Action painting</option>
                <option value="American Realism">American Realism</option>
                <option value="Analytical Cubism">Analytical Cubism</option>
                <option value="Analytical Realism">Analytical Realism</option>
                <option value="Art Brut">Art Brut</option>
                <option value="Art Deco">Art Deco</option>
                <option value="Art Informel">Art Informel</option>
                <option value="Art Nouveau Modern">Art Nouveau Modern</option>
                <option value="Automatic Painting">Automatic Painting</option>
                <option value="Baroque">Baroque</option>
                <option value="Biedermeier">Biedermeier</option>
                <option value="Byzantine">Byzantine</option>
                <option value="Cartographic">Cartographic Art</option>
                <option value="Classicism">Classicism</option>
                <option value="Cloisonnism">Cloisonnism</option>
                <option value="Color Field Painting">Color Field Painting</option>
                <option value="Conceptual Art">Conceptual Art</option>
                <option value="Concretism">Concretism</option>
                <option value="Constructivism">Constructivism</option>
                <option value="Contemporary Realism">Contemporary Realism</option>
                <option value="Costumbrismo">Costumbrismo</option>
                <option value="Cubism">Cubism</option>
                <option value="Cubo-Expressionism">Cubo-Expressionism</option>
                <option value="Cubo-Futurism">Cubo-Futurism</option>
                <option value="Cyber Art">Cyber Art</option>
                <option value="Dada">Dada</option>
                <option value="Divisionism">Divisionism</option>
                <option value="Early Renaissance">Early Renaissance</option>
                <option value="Environmental Land Art">Environmental Land Art</option>
                <option value="Existential Art">Existential Art</option>
                <option value="Expressionism">Expressionism</option>
                <option value="Fantastic Realism">Fantastic Realism</option>
                <option value="Fauvism">Fauvism</option>
                <option value="Feminist Art<">Feminist Art</option>
                <option value="Fiber art">Fiber art</option>
                <option value="Figurative Expressionism">Figurative Expressionism</option>
                <option value="Futurism">Futurism</option>
                <option value="Gongbi">Gongbi</option>
                <option value="Gothic">Gothic</option>
                <option value="Graffiti Art">Graffiti Art</option>
                <option value="Hard Edge Painting">Hard Edge Painting</option>
                <option value="High Renaissance">High Renaissance</option>
                <option value="Hyper-Mannerism Anachronism">Hyper-Mannerism Anachronism</option>
                <option value="Hyper-Realism">Hyper-Realism</option>
                <option value="Ilkhanid">Ilkhanid</option>
                <option value="Impressionism">Impressionism</option>
                <option value="Indian Space painting">Indian Space painting</option>
                <option value="Ink and wash painting">Ink and wash painting</option>
                <option value="International Gothic">International Gothic</option>
                <option value="Intimism">Intimism</option>
                <option value="Japonism">Japonism</option>
                <option value="Joseon Dynasty">Joseon Dynasty</option>
                <option value="Junk Art">Junk Art</option>
                <option value="Kinetic Art">Kinetic Art</option>
                <option value="Kitsch">Kitsch</option>
                <option value="Lettrism">Lettrism</option>
                <option value="Light and Space">Light and Space</option>
                <option value="Lowbrow Art">Lowbrow Art</option>
                <option value="Luminism">Luminism</option>
                <option value="Lyrical Abstraction">Lyrical Abstraction</option>
                <option value="Magic Realism">Magic Realism</option>
                <option value="Mail Art">Mail Art</option>
                <option value="Mannerism Late Renaissance">Mannerism Late Renaissance</option>
                <option value="Maximalism">Maximalism</option>
                <option value="Mechanistic Cubism">Mechanistic Cubism</option>
                <option value="Metaphysical art">Metaphysical art</option>
                <option value="Minimalism">Minimalism</option>
                <option value="Miserablism">Miserablism</option>
                <option value="Modernismo">Modernismo</option>
                <option value="Mosan art">Mosan art</option>
                <option value="Mozarabic">Mozarabic</option>
                <option value="Mughal">Mughal</option>
                <option value="Muralism">Muralism</option>
                <option value="Naïve Art Primitivism">Naïve Art Primitivism</option>
                <option value="Nanga Bunjinga">Nanga Bunjinga</option>
                <option value="Nas-Taliq">Nas-Taliq</option>
                <option value="Native Art">Native Art</option>
                <option value="Naturalism">Naturalism</option>
                <option value="Neo-baroque">Neo-baroque</option>
                <option value="Neo-Byzantine">Neo-Byzantine</option>
                <option value="Neoclassicism">Neoclassicism</option>
                <option value="Neo-Concretism">Neo-Concretism</option>
                <option value="Neo-Dada">Neo-Dada</option>
                <option value="Neo-Expressionism">Neo-Expressionism</option>
                <option value="Neo-Figurative Art">Neo-Figurative Art</option>
                <option value="Neo-Geo">Neo-Geo</option>
                <option value="Neo-Minimalism">Neo-Minimalism</option>
                <option value="Neo-Orthodoxism">Neo-Orthodoxism</option>
                <option value="Neoplasticism">Neoplasticism</option>
                <option value="Neo-Pop Art">Neo-Pop Art</option>
                <option value="Neo-Rococo">Neo-Rococo</option>
                <option value="Neo-Romanticism">Neo-Romanticism</option>
                <option value="New Casualism">New Casualism</option>
                <option value="New European Painting">New European Painting</option>
                <option value="New Realism">New Realism</option>
                <option value="Nihonga">Nihonga</option>
                <option value="Northern Renaissance">Northern Renaissance</option>
                <option value="Nouveau Réalisme">Nouveau Réalisme</option>
                <option value="Op Art">Op Art</option>
                <option value="Orientalism">Orientalism</option>
                <option value="Orphism">Orphism</option>
                <option value="Ottoman Period">Ottoman Period</option>
                <option value="Outsider art">Outsider art</option>
                <option value="P&D Pattern and Decoration">P&D Pattern and Decoration</option>
                <option value="Perceptism">Perceptism</option>
                <option value="Photorealism">Photorealism</option>
                <option value="Pictorialism">Pictorialism</option>
                <option value="Pointillism">Pointillism</option>
                <option value="Pop Art">Pop Art</option>
                <option value="Poster Art Realism<">Poster Art Realism</option>
                <option value="Post-Impressionism">Post-Impressionism</option>
                <option value="Post-Minimalism">Post-Minimalism</option>
                <option value="Post-Painterly Abstraction">Post-Painterly Abstraction</option>
                <option value="Precisionism">Precisionism</option>
                <option value="Primitivism">Primitivism</option>
                <option value="Proto Renaissance">Proto Renaissance</option>
                <option value="Purism">Purism</option>
                <option value="Rayonism">Rayonism</option>
                <option value="Realism">Realism</option>
                <option value="Regionalism">Regionalism</option>
                <option value="Renaissance">Renaissance</option>
                <option value="Rococo">Rococo</option>
                <option value="Romanesque">Romanesque</option>
                <option value="Romanticism">Romanticism</option>
                <option value="Safavid Period">Safavid Period</option>
                <option value="Shin-hanga">Shin-hanga</option>
                <option value="Sky Art">Sky Art</option>
                <option value="Social Realism">Social Realism</option>
                <option value="Socialist Realism">Socialist Realism</option>
                <option value="Sōsaku hanga">Sōsaku hanga</option>
                <option value="Spatialism">Spatialism</option>
                <option value="Spectralism">Spectralism</option>
                <option value="Street art">Street art</option>
                <option value="Street Photography">Street Photography</option>
                <option value="Stuckism">Stuckism</option>
                <option value="Superflat">Superflat</option>
                <option value="Suprematism">Suprematism</option>
                <option value="Surrealism">Surrealism</option>
                <option value="Symbolism">Symbolism</option>
                <option value="Synchromism">Synchromism</option>
                <option value="Synthetic Cubism">Synthetic Cubism</option>
                <option value="Synthetism">Synthetism</option>
                <option value="Tachisme">Tachisme</option>
                <option value="Tenebrism">Tenebrism</option>
                <option value="Timurid Period">Timurid Period</option>
                <option value="Tonalism">Tonalism</option>
                <option value="Toyism">Toyism</option>
                <option value="Transautomatism">Transautomatism</option>
                <option value="Transavantgarde">Transavantgarde</option>
                <option value="Tubism">Tubism</option>
                <option value="Ukiyo-e">Ukiyo-e</option>
                <option value="Verism">Verism</option>
                <option value="Yamato-e">Yamato-e</option>
                <option value="Zen">Zen</option>
            </select>
            <br/>
            <br/>
            <input type="submit" value="Add to Trainings Set" onclick="ProcessingPainting()" class="myButton"/>
            <br/>
            <progress id="progressbar" value="0" max="10000"></progress>
            <p>${requestScope.message}</p>
        </div>
    </form>
    <form method="post" action="RetrainCLassifierServlet">
        <div align="center">
            <br/> <input type="submit" value="Retrain classifier" class="myButton"/>
            <p>${requestScope.message2}</p>
        </div>
    </form>
</div>

<script type='text/javascript'>

    // Progress while processing the painting.
    function ProcessingPainting() {
        var progressbar = $('#progressbar'),
                max = progressbar.attr('max'),
                time = (10000 / max) * 5,
                value = progressbar.val();

        var loading = function () {
            value += 1;
            addValue = progressbar.val(value);

            $('.progress-value').html(value + '%');

            if (value == max) {
                clearInterval(animate);
            }
        };

        var animate = setInterval(function () {
            loading();
        }, time);
    }
    ;

    // Display Register New Admin button only for admin user.
    window.onload = function () {
        if (${IsAdmin == "1"}) {
            document.getElementById("button").style.display = 'block';
            document.getElementById("history_link_to_change").style.display = 'block';
            document.getElementById("settings_Link").style.display = 'block';
        }
        else if (${usernameDisplay == null}|| ${usernameDisplay == "guest"}) {
            document.getElementById("button").style.display = 'none';
            document.getElementById("history_link_to_change").style.display = 'none';
            document.getElementById("settings_Link").style.display = 'none';
        }
        else {
            document.getElementById("button").style.display = 'none';
            document.getElementById("history_link_to_change").style.display = 'block';
            document.getElementById("settings_Link").style.display = 'none';
        }

    }

    // Change Histoy link depending on logged user.
    function ChangeHistoryLink() {
        if (${IsAdmin == "1"}) {
            document.getElementById("history_link_to_change").href = "HistoryForAdminServlet";
        }
        else if (${usernameDisplay == null}) {
            document.getElementById("history_link_to_change").href = "HistoryForGuest";
        }
        else {
            document.getElementById("history_link_to_change").href = "HistoryForUserServlet";
        }
    }

    // Display image immediately after selection.
    function readURL(input) {
        if (input.files && input.files[0]) {
            var reader = new FileReader();

            reader.onload = function (e) {
                $('#blah')
                        .attr('src', e.target.result);
            };

            reader.readAsDataURL(input.files[0]);
        }
    }
</script>

</body>
</html>
