# Jenkins utils

## Documentação
https://jenkins.io/doc/book/pipeline/shared-libraries/

(root)
+- src                     # Groovy source files <br>
|   +- org<br>
|       +- foo<br>
|           +- Bar.groovy  # for org.foo.Bar class<br>
+- vars<br>
|   +- foo.groovy          # for global 'foo' variable<br>
|   +- foo.txt             # help for 'foo' variable<br>
+- resources               # resource files (external libraries only)<br>
|   +- org<br>
|       +- foo<br>
|           +- bar.json    # static helper data for org.foo.Bar<br>