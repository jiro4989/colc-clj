# comblo

[![Build Status](https://travis-ci.org/jiro4989/comblo.svg?branch=master)](https://travis-ci.org/jiro4989/comblo)

CombinatorLogicのClojure実装

## CombinatorLogicとは

CombinatorLogic(コンビネータ論理)はコンビネータを引数にとるコンビネータのみで計
算を行う論理。主要なコンビネータとしてS,K,Iコンビネータが存在する。

### Sコンビネータ

3つの引数をとり、それぞれ以下のようにコンビネータを並べ替える。

```
Sxyz -> xz(yz)
```

### Kコンビネータ

2つの引数をとり、1つ目の引数を返す。

```
Kxy -> x
```

### Iコンビネータ

1つの引数をとり、1つ目の引数を返す。

```
Ix -> x
```

### 計算例

上記のコンビネータを組み合わせた計算例を以下に示す。

```
SKIIx -> KI(II)x -> Ix -> x
```

