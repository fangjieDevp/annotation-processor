package com.ivy.processor;

import com.google.auto.service.AutoService;
import com.ivy.annotation.Id;
import com.ivy.annotation.Persistent;
import com.ivy.annotation.Property;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Name;
import javax.lang.model.element.TypeElement;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Function: com.ivy.processor
 *
 * @author: fangjie07
 * @date: 2018/5/27
 */
@AutoService(Processor.class)
public class MyBatisProcessor extends AbstractProcessor {
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> annotation = new LinkedHashSet<>(3);
        annotation.add(Id.class.getCanonicalName());
        annotation.add(Property.class.getCanonicalName());
        annotation.add(Persistent.class.getCanonicalName());
        return annotation;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        System.err.println("MyBatis processor init succ.");
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        PrintStream ps = null;
        // todo 这么写比较尴尬，用模板生成器比较好
        try {
            for (Element t : roundEnv.getElementsAnnotatedWith(Persistent.class)) {
                //获取正在处理的类名
                Name clazzName = t.getSimpleName();

                // 文件输出名称
                String fileName = clazzName + "Mapper.xml";

                ps = new PrintStream(new FileOutputStream(fileName));

                ps.println("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n" +
                        "<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\" >");

                ps.println("<mapper namespace=\"" + clazzName + "Mapper\">");
                ps.println("<resultMap id=\"BaseResultMap\" type=\"" + clazzName + "\">");
                StringBuilder baseColum = new StringBuilder();
                for (Element ele : t.getEnclosedElements()) {
                    if (ele.getKind() == ElementKind.FIELD) {
                        Id id = ele.getAnnotation(Id.class);
                        if (id != null) {
                            String column = id.column();
                            String type = id.type();
                            // 执行输出
                            ps.println(" <id column=\"" + column + "\" property=\"" + ele.getSimpleName()
                                    + "\" jdbcType=\"" + type.toUpperCase() + "\"/>");
                            baseColum.append(column).append(", ");
                        }

                        Property p = ele.getAnnotation(Property.class);
                        if (p != null) {
                            ps.println("<result column=\"" + p.column() +
                                    "\" property=\"" + ele.getSimpleName() + "\" jdbcType=\"" + p.type() + "\"/>");
                            baseColum.append(p.column()).append(",");
                        }
                    }
                }
                ps.println("</resultMap>");
                ps.println("<sql id=\"Base_Column_List\">");
                ps.println(baseColum.toString().substring(0, baseColum.length() - 1));
                ps.println("</sql>");

                //获取类定义前的@Persistent Annotation
                Persistent per = t.getAnnotation(Persistent.class);
                ps.println("<select id=\"queryAll\" resultMap=\"BaseResultMap\">");
                ps.println("select\n" +
                        "        <include refid=\"Base_Column_List\"/>\n" +
                        "        from " + per.table());
                ps.println("</select>");

                ps.println("</mapper>");
            }

        } catch (Exception e) {

        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }
}
