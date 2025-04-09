package xyz.bluspring.modernnetworking.api

import io.netty.buffer.ByteBuf

/**
 * Composite codecs, designed
 */
object CompositeCodecs {
    @JvmStatic fun <BUF : ByteBuf, TYPE, C1> composite(
        c1: NetworkCodec<C1, in BUF>, g1: (TYPE) -> C1,
        creator: (C1) -> TYPE
    ) = NetworkCodec<TYPE, BUF>({ buf, t ->
        c1.encode(buf, g1.invoke(t))
    }, { buf ->
        creator.invoke(c1.decode(buf))
    })

    @JvmStatic fun <BUF : ByteBuf, TYPE, C1, C2> composite(
        c1: NetworkCodec<C1, in BUF>, g1: (TYPE) -> C1,
        c2: NetworkCodec<C2, in BUF>, g2: (TYPE) -> C2,
        creator: (C1, C2) -> TYPE
    ) = NetworkCodec<TYPE, BUF>({ buf, t ->
        c1.encode(buf, g1.invoke(t))
        c2.encode(buf, g2.invoke(t))
    }, { buf ->
        creator.invoke(c1.decode(buf), c2.decode(buf))
    })

    @JvmStatic fun <BUF : ByteBuf, TYPE, C1, C2, C3> composite(
        c1: NetworkCodec<C1, in BUF>, g1: (TYPE) -> C1,
        c2: NetworkCodec<C2, in BUF>, g2: (TYPE) -> C2,
        c3: NetworkCodec<C3, in BUF>, g3: (TYPE) -> C3,
        creator: (C1, C2, C3) -> TYPE
    ) = NetworkCodec<TYPE, BUF>({ buf, t ->
        c1.encode(buf, g1.invoke(t))
        c2.encode(buf, g2.invoke(t))
        c3.encode(buf, g3.invoke(t))
    }, { buf ->
        creator.invoke(c1.decode(buf), c2.decode(buf), c3.decode(buf))
    })

    @JvmStatic fun <BUF : ByteBuf, TYPE, C1, C2, C3, C4> composite(
        c1: NetworkCodec<C1, in BUF>, g1: (TYPE) -> C1,
        c2: NetworkCodec<C2, in BUF>, g2: (TYPE) -> C2,
        c3: NetworkCodec<C3, in BUF>, g3: (TYPE) -> C3,
        c4: NetworkCodec<C4, in BUF>, g4: (TYPE) -> C4,
        creator: (C1, C2, C3, C4) -> TYPE
    ) = NetworkCodec<TYPE, BUF>({ buf, t ->
        c1.encode(buf, g1.invoke(t))
        c2.encode(buf, g2.invoke(t))
        c3.encode(buf, g3.invoke(t))
        c4.encode(buf, g4.invoke(t))
    }, { buf ->
        creator.invoke(c1.decode(buf), c2.decode(buf), c3.decode(buf), c4.decode(buf))
    })

    @JvmStatic fun <BUF : ByteBuf, TYPE, C1, C2, C3, C4, C5> composite(
        c1: NetworkCodec<C1, in BUF>, g1: (TYPE) -> C1,
        c2: NetworkCodec<C2, in BUF>, g2: (TYPE) -> C2,
        c3: NetworkCodec<C3, in BUF>, g3: (TYPE) -> C3,
        c4: NetworkCodec<C4, in BUF>, g4: (TYPE) -> C4,
        c5: NetworkCodec<C5, in BUF>, g5: (TYPE) -> C5,
        creator: (C1, C2, C3, C4, C5) -> TYPE
    ) = NetworkCodec<TYPE, BUF>({ buf, t ->
        c1.encode(buf, g1.invoke(t))
        c2.encode(buf, g2.invoke(t))
        c3.encode(buf, g3.invoke(t))
        c4.encode(buf, g4.invoke(t))
        c5.encode(buf, g5.invoke(t))
    }, { buf ->
        creator.invoke(c1.decode(buf), c2.decode(buf), c3.decode(buf), c4.decode(buf), c5.decode(buf))
    })

    @JvmStatic fun <BUF : ByteBuf, TYPE, C1, C2, C3, C4, C5, C6> composite(
        c1: NetworkCodec<C1, in BUF>, g1: (TYPE) -> C1,
        c2: NetworkCodec<C2, in BUF>, g2: (TYPE) -> C2,
        c3: NetworkCodec<C3, in BUF>, g3: (TYPE) -> C3,
        c4: NetworkCodec<C4, in BUF>, g4: (TYPE) -> C4,
        c5: NetworkCodec<C5, in BUF>, g5: (TYPE) -> C5,
        c6: NetworkCodec<C6, in BUF>, g6: (TYPE) -> C6,
        creator: (C1, C2, C3, C4, C5, C6) -> TYPE
    ) = NetworkCodec<TYPE, BUF>({ buf, t ->
        c1.encode(buf, g1.invoke(t))
        c2.encode(buf, g2.invoke(t))
        c3.encode(buf, g3.invoke(t))
        c4.encode(buf, g4.invoke(t))
        c5.encode(buf, g5.invoke(t))
        c6.encode(buf, g6.invoke(t))
    }, { buf ->
        creator.invoke(c1.decode(buf), c2.decode(buf), c3.decode(buf), c4.decode(buf), c5.decode(buf), c6.decode(buf))
    })

    @JvmStatic fun <BUF : ByteBuf, TYPE, C1, C2, C3, C4, C5, C6, C7> composite(
        c1: NetworkCodec<C1, in BUF>, g1: (TYPE) -> C1,
        c2: NetworkCodec<C2, in BUF>, g2: (TYPE) -> C2,
        c3: NetworkCodec<C3, in BUF>, g3: (TYPE) -> C3,
        c4: NetworkCodec<C4, in BUF>, g4: (TYPE) -> C4,
        c5: NetworkCodec<C5, in BUF>, g5: (TYPE) -> C5,
        c6: NetworkCodec<C6, in BUF>, g6: (TYPE) -> C6,
        c7: NetworkCodec<C7, in BUF>, g7: (TYPE) -> C7,
        creator: (C1, C2, C3, C4, C5, C6, C7) -> TYPE
    ) = NetworkCodec<TYPE, BUF>({ buf, t ->
        c1.encode(buf, g1.invoke(t))
        c2.encode(buf, g2.invoke(t))
        c3.encode(buf, g3.invoke(t))
        c4.encode(buf, g4.invoke(t))
        c5.encode(buf, g5.invoke(t))
        c6.encode(buf, g6.invoke(t))
        c7.encode(buf, g7.invoke(t))
    }, { buf ->
        creator.invoke(c1.decode(buf), c2.decode(buf), c3.decode(buf), c4.decode(buf), c5.decode(buf), c6.decode(buf), c7.decode(buf))
    })

    @JvmStatic fun <BUF : ByteBuf, TYPE, C1, C2, C3, C4, C5, C6, C7, C8> composite(
        c1: NetworkCodec<C1, in BUF>, g1: (TYPE) -> C1,
        c2: NetworkCodec<C2, in BUF>, g2: (TYPE) -> C2,
        c3: NetworkCodec<C3, in BUF>, g3: (TYPE) -> C3,
        c4: NetworkCodec<C4, in BUF>, g4: (TYPE) -> C4,
        c5: NetworkCodec<C5, in BUF>, g5: (TYPE) -> C5,
        c6: NetworkCodec<C6, in BUF>, g6: (TYPE) -> C6,
        c7: NetworkCodec<C7, in BUF>, g7: (TYPE) -> C7,
        c8: NetworkCodec<C8, in BUF>, g8: (TYPE) -> C8,
        creator: (C1, C2, C3, C4, C5, C6, C7, C8) -> TYPE
    ) = NetworkCodec<TYPE, BUF>({ buf, t ->
        c1.encode(buf, g1.invoke(t))
        c2.encode(buf, g2.invoke(t))
        c3.encode(buf, g3.invoke(t))
        c4.encode(buf, g4.invoke(t))
        c5.encode(buf, g5.invoke(t))
        c6.encode(buf, g6.invoke(t))
        c7.encode(buf, g7.invoke(t))
        c8.encode(buf, g8.invoke(t))
    }, { buf ->
        creator.invoke(c1.decode(buf), c2.decode(buf), c3.decode(buf), c4.decode(buf), c5.decode(buf), c6.decode(buf), c7.decode(buf), c8.decode(buf))
    })

    @JvmStatic fun <BUF : ByteBuf, TYPE, C1, C2, C3, C4, C5, C6, C7, C8, C9> composite(
        c1: NetworkCodec<C1, in BUF>, g1: (TYPE) -> C1,
        c2: NetworkCodec<C2, in BUF>, g2: (TYPE) -> C2,
        c3: NetworkCodec<C3, in BUF>, g3: (TYPE) -> C3,
        c4: NetworkCodec<C4, in BUF>, g4: (TYPE) -> C4,
        c5: NetworkCodec<C5, in BUF>, g5: (TYPE) -> C5,
        c6: NetworkCodec<C6, in BUF>, g6: (TYPE) -> C6,
        c7: NetworkCodec<C7, in BUF>, g7: (TYPE) -> C7,
        c8: NetworkCodec<C8, in BUF>, g8: (TYPE) -> C8,
        c9: NetworkCodec<C9, in BUF>, g9: (TYPE) -> C9,
        creator: (C1, C2, C3, C4, C5, C6, C7, C8, C9) -> TYPE
    ) = NetworkCodec<TYPE, BUF>({ buf, t ->
        c1.encode(buf, g1.invoke(t))
        c2.encode(buf, g2.invoke(t))
        c3.encode(buf, g3.invoke(t))
        c4.encode(buf, g4.invoke(t))
        c5.encode(buf, g5.invoke(t))
        c6.encode(buf, g6.invoke(t))
        c7.encode(buf, g7.invoke(t))
        c8.encode(buf, g8.invoke(t))
        c9.encode(buf, g9.invoke(t))
    }, { buf ->
        creator.invoke(c1.decode(buf), c2.decode(buf), c3.decode(buf), c4.decode(buf), c5.decode(buf), c6.decode(buf), c7.decode(buf), c8.decode(buf), c9.decode(buf))
    })

    @JvmStatic fun <BUF : ByteBuf, TYPE, C1, C2, C3, C4, C5, C6, C7, C8, C9, C10> composite(
        c1: NetworkCodec<C1, in BUF>, g1: (TYPE) -> C1,
        c2: NetworkCodec<C2, in BUF>, g2: (TYPE) -> C2,
        c3: NetworkCodec<C3, in BUF>, g3: (TYPE) -> C3,
        c4: NetworkCodec<C4, in BUF>, g4: (TYPE) -> C4,
        c5: NetworkCodec<C5, in BUF>, g5: (TYPE) -> C5,
        c6: NetworkCodec<C6, in BUF>, g6: (TYPE) -> C6,
        c7: NetworkCodec<C7, in BUF>, g7: (TYPE) -> C7,
        c8: NetworkCodec<C8, in BUF>, g8: (TYPE) -> C8,
        c9: NetworkCodec<C9, in BUF>, g9: (TYPE) -> C9,
        c10: NetworkCodec<C10, in BUF>, g10: (TYPE) -> C10,
        creator: (C1, C2, C3, C4, C5, C6, C7, C8, C9, C10) -> TYPE
    ) = NetworkCodec<TYPE, BUF>({ buf, t ->
        c1.encode(buf, g1.invoke(t))
        c2.encode(buf, g2.invoke(t))
        c3.encode(buf, g3.invoke(t))
        c4.encode(buf, g4.invoke(t))
        c5.encode(buf, g5.invoke(t))
        c6.encode(buf, g6.invoke(t))
        c7.encode(buf, g7.invoke(t))
        c8.encode(buf, g8.invoke(t))
        c9.encode(buf, g9.invoke(t))
        c10.encode(buf, g10.invoke(t))
    }, { buf ->
        creator.invoke(c1.decode(buf), c2.decode(buf), c3.decode(buf), c4.decode(buf), c5.decode(buf), c6.decode(buf), c7.decode(buf), c8.decode(buf), c9.decode(buf), c10.decode(buf))
    })

    @JvmStatic fun <BUF : ByteBuf, TYPE, C1, C2, C3, C4, C5, C6, C7, C8, C9, C10, C11> composite(
        c1: NetworkCodec<C1, in BUF>, g1: (TYPE) -> C1,
        c2: NetworkCodec<C2, in BUF>, g2: (TYPE) -> C2,
        c3: NetworkCodec<C3, in BUF>, g3: (TYPE) -> C3,
        c4: NetworkCodec<C4, in BUF>, g4: (TYPE) -> C4,
        c5: NetworkCodec<C5, in BUF>, g5: (TYPE) -> C5,
        c6: NetworkCodec<C6, in BUF>, g6: (TYPE) -> C6,
        c7: NetworkCodec<C7, in BUF>, g7: (TYPE) -> C7,
        c8: NetworkCodec<C8, in BUF>, g8: (TYPE) -> C8,
        c9: NetworkCodec<C9, in BUF>, g9: (TYPE) -> C9,
        c10: NetworkCodec<C10, in BUF>, g10: (TYPE) -> C10,
        c11: NetworkCodec<C11, in BUF>, g11: (TYPE) -> C11,
        creator: (C1, C2, C3, C4, C5, C6, C7, C8, C9, C10, C11) -> TYPE
    ) = NetworkCodec<TYPE, BUF>({ buf, t ->
        c1.encode(buf, g1.invoke(t))
        c2.encode(buf, g2.invoke(t))
        c3.encode(buf, g3.invoke(t))
        c4.encode(buf, g4.invoke(t))
        c5.encode(buf, g5.invoke(t))
        c6.encode(buf, g6.invoke(t))
        c7.encode(buf, g7.invoke(t))
        c8.encode(buf, g8.invoke(t))
        c9.encode(buf, g9.invoke(t))
        c10.encode(buf, g10.invoke(t))
        c11.encode(buf, g11.invoke(t))
    }, { buf ->
        creator.invoke(c1.decode(buf), c2.decode(buf), c3.decode(buf), c4.decode(buf), c5.decode(buf), c6.decode(buf), c7.decode(buf), c8.decode(buf), c9.decode(buf), c10.decode(buf), c11.decode(buf))
    })

    @JvmStatic fun <BUF : ByteBuf, TYPE, C1, C2, C3, C4, C5, C6, C7, C8, C9, C10, C11, C12> composite(
        c1: NetworkCodec<C1, in BUF>, g1: (TYPE) -> C1,
        c2: NetworkCodec<C2, in BUF>, g2: (TYPE) -> C2,
        c3: NetworkCodec<C3, in BUF>, g3: (TYPE) -> C3,
        c4: NetworkCodec<C4, in BUF>, g4: (TYPE) -> C4,
        c5: NetworkCodec<C5, in BUF>, g5: (TYPE) -> C5,
        c6: NetworkCodec<C6, in BUF>, g6: (TYPE) -> C6,
        c7: NetworkCodec<C7, in BUF>, g7: (TYPE) -> C7,
        c8: NetworkCodec<C8, in BUF>, g8: (TYPE) -> C8,
        c9: NetworkCodec<C9, in BUF>, g9: (TYPE) -> C9,
        c10: NetworkCodec<C10, in BUF>, g10: (TYPE) -> C10,
        c11: NetworkCodec<C11, in BUF>, g11: (TYPE) -> C11,
        c12: NetworkCodec<C12, in BUF>, g12: (TYPE) -> C12,
        creator: (C1, C2, C3, C4, C5, C6, C7, C8, C9, C10, C11, C12) -> TYPE
    ) = NetworkCodec<TYPE, BUF>({ buf, t ->
        c1.encode(buf, g1.invoke(t))
        c2.encode(buf, g2.invoke(t))
        c3.encode(buf, g3.invoke(t))
        c4.encode(buf, g4.invoke(t))
        c5.encode(buf, g5.invoke(t))
        c6.encode(buf, g6.invoke(t))
        c7.encode(buf, g7.invoke(t))
        c8.encode(buf, g8.invoke(t))
        c9.encode(buf, g9.invoke(t))
        c10.encode(buf, g10.invoke(t))
        c11.encode(buf, g11.invoke(t))
        c12.encode(buf, g12.invoke(t))
    }, { buf ->
        creator.invoke(c1.decode(buf), c2.decode(buf), c3.decode(buf), c4.decode(buf), c5.decode(buf), c6.decode(buf), c7.decode(buf), c8.decode(buf), c9.decode(buf), c10.decode(buf), c11.decode(buf), c12.decode(buf))
    })

    @JvmStatic fun <BUF : ByteBuf, TYPE, C1, C2, C3, C4, C5, C6, C7, C8, C9, C10, C11, C12, C13> composite(
        c1: NetworkCodec<C1, in BUF>, g1: (TYPE) -> C1,
        c2: NetworkCodec<C2, in BUF>, g2: (TYPE) -> C2,
        c3: NetworkCodec<C3, in BUF>, g3: (TYPE) -> C3,
        c4: NetworkCodec<C4, in BUF>, g4: (TYPE) -> C4,
        c5: NetworkCodec<C5, in BUF>, g5: (TYPE) -> C5,
        c6: NetworkCodec<C6, in BUF>, g6: (TYPE) -> C6,
        c7: NetworkCodec<C7, in BUF>, g7: (TYPE) -> C7,
        c8: NetworkCodec<C8, in BUF>, g8: (TYPE) -> C8,
        c9: NetworkCodec<C9, in BUF>, g9: (TYPE) -> C9,
        c10: NetworkCodec<C10, in BUF>, g10: (TYPE) -> C10,
        c11: NetworkCodec<C11, in BUF>, g11: (TYPE) -> C11,
        c12: NetworkCodec<C12, in BUF>, g12: (TYPE) -> C12,
        c13: NetworkCodec<C13, in BUF>, g13: (TYPE) -> C13,
        creator: (C1, C2, C3, C4, C5, C6, C7, C8, C9, C10, C11, C12, C13) -> TYPE
    ) = NetworkCodec<TYPE, BUF>({ buf, t ->
        c1.encode(buf, g1.invoke(t))
        c2.encode(buf, g2.invoke(t))
        c3.encode(buf, g3.invoke(t))
        c4.encode(buf, g4.invoke(t))
        c5.encode(buf, g5.invoke(t))
        c6.encode(buf, g6.invoke(t))
        c7.encode(buf, g7.invoke(t))
        c8.encode(buf, g8.invoke(t))
        c9.encode(buf, g9.invoke(t))
        c10.encode(buf, g10.invoke(t))
        c11.encode(buf, g11.invoke(t))
        c12.encode(buf, g12.invoke(t))
        c13.encode(buf, g13.invoke(t))
    }, { buf ->
        creator.invoke(c1.decode(buf), c2.decode(buf), c3.decode(buf), c4.decode(buf), c5.decode(buf), c6.decode(buf), c7.decode(buf), c8.decode(buf), c9.decode(buf), c10.decode(buf), c11.decode(buf), c12.decode(buf), c13.decode(buf))
    })

    @JvmStatic fun <BUF : ByteBuf, TYPE, C1, C2, C3, C4, C5, C6, C7, C8, C9, C10, C11, C12, C13, C14> composite(
        c1: NetworkCodec<C1, in BUF>, g1: (TYPE) -> C1,
        c2: NetworkCodec<C2, in BUF>, g2: (TYPE) -> C2,
        c3: NetworkCodec<C3, in BUF>, g3: (TYPE) -> C3,
        c4: NetworkCodec<C4, in BUF>, g4: (TYPE) -> C4,
        c5: NetworkCodec<C5, in BUF>, g5: (TYPE) -> C5,
        c6: NetworkCodec<C6, in BUF>, g6: (TYPE) -> C6,
        c7: NetworkCodec<C7, in BUF>, g7: (TYPE) -> C7,
        c8: NetworkCodec<C8, in BUF>, g8: (TYPE) -> C8,
        c9: NetworkCodec<C9, in BUF>, g9: (TYPE) -> C9,
        c10: NetworkCodec<C10, in BUF>, g10: (TYPE) -> C10,
        c11: NetworkCodec<C11, in BUF>, g11: (TYPE) -> C11,
        c12: NetworkCodec<C12, in BUF>, g12: (TYPE) -> C12,
        c13: NetworkCodec<C13, in BUF>, g13: (TYPE) -> C13,
        c14: NetworkCodec<C14, in BUF>, g14: (TYPE) -> C14,
        creator: (C1, C2, C3, C4, C5, C6, C7, C8, C9, C10, C11, C12, C13, C14) -> TYPE
    ) = NetworkCodec<TYPE, BUF>({ buf, t ->
        c1.encode(buf, g1.invoke(t))
        c2.encode(buf, g2.invoke(t))
        c3.encode(buf, g3.invoke(t))
        c4.encode(buf, g4.invoke(t))
        c5.encode(buf, g5.invoke(t))
        c6.encode(buf, g6.invoke(t))
        c7.encode(buf, g7.invoke(t))
        c8.encode(buf, g8.invoke(t))
        c9.encode(buf, g9.invoke(t))
        c10.encode(buf, g10.invoke(t))
        c11.encode(buf, g11.invoke(t))
        c12.encode(buf, g12.invoke(t))
        c13.encode(buf, g13.invoke(t))
        c14.encode(buf, g14.invoke(t))
    }, { buf ->
        creator.invoke(c1.decode(buf), c2.decode(buf), c3.decode(buf), c4.decode(buf), c5.decode(buf), c6.decode(buf), c7.decode(buf), c8.decode(buf), c9.decode(buf), c10.decode(buf), c11.decode(buf), c12.decode(buf), c13.decode(buf), c14.decode(buf))
    })

    @JvmStatic fun <BUF : ByteBuf, TYPE, C1, C2, C3, C4, C5, C6, C7, C8, C9, C10, C11, C12, C13, C14, C15> composite(
        c1: NetworkCodec<C1, in BUF>, g1: (TYPE) -> C1,
        c2: NetworkCodec<C2, in BUF>, g2: (TYPE) -> C2,
        c3: NetworkCodec<C3, in BUF>, g3: (TYPE) -> C3,
        c4: NetworkCodec<C4, in BUF>, g4: (TYPE) -> C4,
        c5: NetworkCodec<C5, in BUF>, g5: (TYPE) -> C5,
        c6: NetworkCodec<C6, in BUF>, g6: (TYPE) -> C6,
        c7: NetworkCodec<C7, in BUF>, g7: (TYPE) -> C7,
        c8: NetworkCodec<C8, in BUF>, g8: (TYPE) -> C8,
        c9: NetworkCodec<C9, in BUF>, g9: (TYPE) -> C9,
        c10: NetworkCodec<C10, in BUF>, g10: (TYPE) -> C10,
        c11: NetworkCodec<C11, in BUF>, g11: (TYPE) -> C11,
        c12: NetworkCodec<C12, in BUF>, g12: (TYPE) -> C12,
        c13: NetworkCodec<C13, in BUF>, g13: (TYPE) -> C13,
        c14: NetworkCodec<C14, in BUF>, g14: (TYPE) -> C14,
        c15: NetworkCodec<C15, in BUF>, g15: (TYPE) -> C15,
        creator: (C1, C2, C3, C4, C5, C6, C7, C8, C9, C10, C11, C12, C13, C14, C15) -> TYPE
    ) = NetworkCodec<TYPE, BUF>({ buf, t ->
        c1.encode(buf, g1.invoke(t))
        c2.encode(buf, g2.invoke(t))
        c3.encode(buf, g3.invoke(t))
        c4.encode(buf, g4.invoke(t))
        c5.encode(buf, g5.invoke(t))
        c6.encode(buf, g6.invoke(t))
        c7.encode(buf, g7.invoke(t))
        c8.encode(buf, g8.invoke(t))
        c9.encode(buf, g9.invoke(t))
        c10.encode(buf, g10.invoke(t))
        c11.encode(buf, g11.invoke(t))
        c12.encode(buf, g12.invoke(t))
        c13.encode(buf, g13.invoke(t))
        c14.encode(buf, g14.invoke(t))
        c15.encode(buf, g15.invoke(t))
    }, { buf ->
        creator.invoke(c1.decode(buf), c2.decode(buf), c3.decode(buf), c4.decode(buf), c5.decode(buf), c6.decode(buf), c7.decode(buf), c8.decode(buf), c9.decode(buf), c10.decode(buf), c11.decode(buf), c12.decode(buf), c13.decode(buf), c14.decode(buf), c15.decode(buf))
    })
}