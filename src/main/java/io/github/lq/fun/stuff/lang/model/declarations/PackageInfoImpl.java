package io.github.lq.fun.stuff.lang.model.declarations;

import jakarta.enterprise.lang.model.declarations.PackageInfo;

import java.util.Objects;

public class PackageInfoImpl extends DeclarationInfoImpl implements PackageInfo {
    private final Package pkg;

    private final String name;
    public PackageInfoImpl(Package pkg) {
        super(pkg);
        this.pkg = pkg;
        this.name = pkg.getName();
    }

    @Override
    public String name() {
        return pkg.getName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PackageInfoImpl that = (PackageInfoImpl) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
