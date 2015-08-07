languages = ['as3', 'c_glib', 'cpp', 'csharp', 'd', 'delphi', 'erl', 'go', 'haxe', 'hs', 'java', 'js', 'nodejs', 'ocaml', 'perl', 'php', 'py', 'py.tornado', 'py.twisted', 'rb']

for language in languages:
  print """                    <execution>
                        <id>thrift-{language}-sources</id>
                        <phase>generate-sources</phase>
                        <configuration>
                            <generator>{language}</generator>
                            <outputDirectory>target/generated-sources/thrift/{language}</outputDirectory>
                        </configuration>
                        <goals>
                            <goal>compile</goal>
                        </goals>
                    </execution>""".format(language=language)