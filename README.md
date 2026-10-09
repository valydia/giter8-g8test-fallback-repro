# Giter8 `g8Test` fallback reproducer

This minimal template demonstrates that `g8Test` can report a Giter8 rendering error but still succeed, whereas the standalone `g8` CLI fails for the same template.

The template contains an unescaped Scala interpolation dollar in `src/main/g8/project/resolvers.sbt`:

```scala
val endpoint = s"https://$host"
```

Giter8 requires the dollar to be written as `\$` in a template source file.

## Reproduce

### `g8Test` succeeds unexpectedly

```bash
sbt g8Test
```

Expected observed output:

```text
Falling back to file copy for .../src/main/g8/project/resolvers.sbt: ...
[success] Total time: ...
```

The rendering error is detected but is treated as recoverable: the file is copied unchanged and the scripted test passes.

### Standalone `g8` correctly fails

From a directory other than this repository:

```bash
g8 file:///absolute/path/to/g8test-fallback-repro --name=test-template-service
```

Expected observed output:

```text
Exiting due to error in the template
... An unexpected error occurred while processing the template.
Check that all literal '$' are properly escaped with '\$'
```

## Expected behaviour

`g8Test` should fail when a non-verbatim template file cannot be rendered, matching the standalone `g8` CLI. Alternatively, it should provide a strict mode that makes its fallback-to-copy warning fatal.
