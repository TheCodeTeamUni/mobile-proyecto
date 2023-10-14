# MISW4203-Vinilos-Grupo18

Vinyls es una aplicación que permite la visualización y gestión de información relacionada con música, como por ejemplo álbumes, artistas, coleccionistas	, entre otros. Para la versión móvil se usará una API REST que está previamente desarrollada y es muy similar a la API que estamos usando en la versión web de Vinilos.

Puede encontrar más información sobre el proyecto en la wiki https://github.com/manuel-morales-sa/MISW4203-Vinilos/wiki

## Instrucciones para construir la aplicación de forma local

1. Tener instalado Android Studio (Guía https://misovirtual.virtual.uniandes.edu.co/codelabs/android-setup-tutorial/index.html#2)
2. Clonar el repositorio
3. Abrir el proyecto con Android Studio
4. Sincronizar el archivo build.gradle para configurar las versiones de los sistemas de su máquina
5. Ejecutar la aplicación mediante alguna de las siguientes formas:
    1. **Ejecución en un celular:** Descargue en su celular el archivo **app-debug.apk** e instalelo . Una vez instalado, asegurese de tener conexión a internet para ejecutar la aplicación. Puede encontrar el archivo en cualquiera de las siguientes ubicaciones:
        1. En el repositorio: **app-moviles-vinilos/app/build/outputs/apk/debug/app-debug.apk**
        2. El el siguiente enlace: [app-debug.apk](https://uniandes.sharepoint.com/sites/EquipodeestudioMISO/Documentos%20compartidos/Forms/AllItems.aspx?id=%2Fsites%2FEquipodeestudioMISO%2FDocumentos%20compartidos%2FGeneral%2FAplicaciones%20Moviles%2Fapp%2Ddebug%2Eapk&parent=%2Fsites%2FEquipodeestudioMISO%2FDocumentos%20compartidos%2FGeneral%2FAplicaciones%20Moviles&p=true&ga=1)
    2. **Ejecución en el emulador de Android Studio:** Puede ejecutar la aplicación desde un emulador Android desde Android Studio, solo debe crearlo, seleccionarlo y ejecutarlo. Para ello puede seguir las intrucciones del siguiente link https://developer.android.com/codelabs/kotlin-android-training-get-started#5
    3. **Emulación de la aplicación en un celular:** Puede usar su dispositivo Android como emulador y ejecutar la aplicación desde su máquina. Para ello siga las instrucciones del siguiente link https://developer.android.com/studio/debug/dev-options
    
## Instrucciones para ejecutar las pruebas E2E en Espresso

1. Desde Android Studio se deben ejecutar los archivos existentes en la carpeta: **app/src/androidTest/java/com.example.vinilos/album.test/**
Cada archivo de esta ubicación representa un escenario de pruebas 
2. Hacer click derecho en cada archivo de pruebas
3. Hacer click en **Run 'NombreArchivo'**

## Ver release asociado a una versión.
Para visualizar el release asociado a una versión debe dar click en la lista de branches y seleccionar Tags, de esta manera puede visualizar los cambios correspondientes a la iteración, note que en la parte derecha se encuentra la relacion de la versión al release, aca se puede evidenciar de mejor manera.
![release](https://user-images.githubusercontent.com/98921573/202888580-323a1c54-f45c-449c-82dd-c2977e79208f.png)
