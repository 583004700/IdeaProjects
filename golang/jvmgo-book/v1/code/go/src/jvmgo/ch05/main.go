package main

import "fmt"
import "strings"
import "jvmgo/ch05/classfile"
import "jvmgo/ch05/classpath"

func main() {
	cmd := parseCmd()

	if cmd.versionFlag {
		fmt.Println("version 0.0.1")
	} else {
		startJVM(cmd)
	}
}

func startJVM(cmd *Cmd) {
	//cp := classpath.Parse(cmd.XjreOption, cmd.cpOption)
	cp := classpath.Parse("C:\\Program Files\\Java\\jre1.8.0_121", "E:\\IdeaProjects\\golang\\out")
	className := strings.Replace("jvmgo.book.ch05.GaussTest", ".", "/", -1)
	cf := loadClass(className, cp)
	mainMethod := getMainMethod(cf)
	if mainMethod != nil {
		interpret(mainMethod)
	} else {
		fmt.Printf("Main method not found in class %s\n", cmd.class)
	}
}

func loadClass(className string, cp *classpath.Classpath) *classfile.ClassFile {
	classData, _, err := cp.ReadClass(className)
	if err != nil {
		panic(err)
	}

	cf, err := classfile.Parse(classData)
	if err != nil {
		panic(err)
	}

	return cf
}

func getMainMethod(cf *classfile.ClassFile) *classfile.MemberInfo {
	for _, m := range cf.Methods() {
		if m.Name() == "main" && m.Descriptor() == "([Ljava/lang/String;)V" {
			return m
		}
	}
	return nil
}
