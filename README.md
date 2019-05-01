# Prop Util

This project is a command-line Java application for managing Java properties files, with no
external dependencies.

# Example usage

Given a properties file with the following content:

```
foo.bim.bam = bar
dooh = mybad
```

the following command:

```sh
java -jar sn-prop-util.jar modify --file foo.properties --set foo=bar --delete dooh --add foo.bim.bam=blah
```

would modify the file so it contains the following content:

```
foo.bim.bam = bar
foo = bar
```

The `--set` option unconditionally sets a property value (overwriting any existing value), the
`--delete` option deletes a property value, and the `--add` option only adds a property if that
property does not already exist.
