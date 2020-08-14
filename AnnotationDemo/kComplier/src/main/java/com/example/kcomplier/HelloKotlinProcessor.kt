package com.example.kcomplier

import com.squareup.javapoet.JavaFile
import com.squareup.javapoet.MethodSpec
import com.squareup.javapoet.TypeSpec
import java.io.IOException
import java.util.*
import javax.annotation.processing.*
import javax.lang.model.SourceVersion
import javax.lang.model.element.Element
import javax.lang.model.element.Modifier
import javax.lang.model.element.TypeElement
import javax.lang.model.util.Elements
import javax.lang.model.util.Types
import javax.tools.Diagnostic

class HelloKotlinProcessor : AbstractProcessor(){
     var mTypeUtils: Types? = null
    var mElementUtils: Elements? = null
    var mFiler: Filer? = null
     var mMessager: Messager? = null

    @Synchronized
    override fun init(processingEnv: ProcessingEnvironment) {
        super.init(processingEnv)
        mTypeUtils = processingEnv.typeUtils
        mElementUtils = processingEnv.elementUtils
        mFiler = processingEnv.filer
        mMessager = processingEnv.messager
    }

    override fun getSupportedSourceVersion(): SourceVersion? {
        return SourceVersion.latestSupported()
    }

    override fun getSupportedAnnotationTypes(): Set<String>? {
        val annotations: MutableSet<String> = LinkedHashSet()
        annotations.add(HelloKotlin::class.java.getCanonicalName())
        return annotations
    }
    override fun process(annotations: MutableSet<out TypeElement>?, roundEnv: RoundEnvironment?): Boolean {
        info(">>> Found field, start... <<<")

        if (annotations != null) {
            for (element in annotations) {
                if (element.qualifiedName.toString() == HelloKotlin::class.java.getCanonicalName()) { // main method
                    val main: MethodSpec = MethodSpec.methodBuilder("main")
                            .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                            .returns(Void.TYPE)
                            .addParameter(Array<String>::class.java, "args")
                            .addStatement("\$T.out.println(\$S)", System::class.java, "Hello, JavaPoet!")
                            .build()
                    // HelloWorld class
                    val helloWorld: TypeSpec = TypeSpec.classBuilder("HelloWorld")
                            .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                            .addMethod(main)
                            .build()
                    try { // build com.example.HelloWorld.java
                        val javaFile: JavaFile = JavaFile.builder("com.example.kcomplier", helloWorld)
                                .addFileComment(" This codes are generated automatically. Do not modify!")
                                .build()
                        // write to file
                        javaFile.writeTo(mFiler)
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
            }
        }

        return true
    }
    private fun info(msg: String, vararg args: Any) {
        mMessager?.printMessage(
                Diagnostic.Kind.NOTE, String.format(msg, *args))
    }
}