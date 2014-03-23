# transient-test

A project to show how difficult finding
[CLJ-1285](http://dev.clojure.org/jira/browse/CLJ-1285) would have been with
traditional unit-testing. To compare property-based testing with traditional
unit-testing, see the [unit-tests](test/transient_test/unit_test.clj) and the
[property-based test](test/transient_test/property_test.clj).

## Usage

```shell
git clone git@github.com:reiddraper/clojure-transient-test.git
cd clojure-transient-test
lein test
```

## License

Copyright © 2014 Reid Draper

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
